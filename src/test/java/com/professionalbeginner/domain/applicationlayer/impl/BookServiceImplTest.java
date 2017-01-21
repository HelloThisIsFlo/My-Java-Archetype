package com.professionalbeginner.domain.applicationlayer.impl;

import com.professionalbeginner.TestUtils;
import com.professionalbeginner.data.inmemory.InMemoryBookRepository;
import com.professionalbeginner.data.inmemory.InMemoryUserRepository;
import com.professionalbeginner.domain.applicationlayer.BookService;
import com.professionalbeginner.domain.core.book.*;
import com.professionalbeginner.domain.core.book.IllegalReviewException;
import com.professionalbeginner.domain.core.book.Rating;
import com.professionalbeginner.domain.core.user.UserId;
import com.professionalbeginner.domain.interfacelayer.repository.BookNotFoundException;
import com.professionalbeginner.domain.interfacelayer.repository.BookRepository;
import com.professionalbeginner.domain.interfacelayer.repository.UserRepository;
import com.professionalbeginner.domain.interfacelayer.statistics.StatisticsContract;
import com.professionalbeginner.statisticsmodule.StatisticsContractImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * @author Kempenich Florian
 */
public class BookServiceImplTest {

    private BookRepository bookRepository;
    private UserRepository userRepository;
    private BookService bookService;

    private UserId validUsername;
    private UserId otherValidUsername;

    private TestUtils testUtils;

    @Before
    public void setUp() throws Exception {
        testUtils = new TestUtils();

        bookRepository = new InMemoryBookRepository();
        userRepository = new InMemoryUserRepository();
        StatisticsContract statistics = new StatisticsContractImpl();
        bookService = new BookServiceImpl(bookRepository, userRepository, statistics);

        initValidUsers();
    }

    private void initValidUsers() {
        validUsername = new UserId("patrick");
        otherValidUsername = new UserId("Frank34");
        userRepository.save(testUtils.makeRandomUser(validUsername));
        userRepository.save(testUtils.makeRandomUser(otherValidUsername));
    }

    @Test
    public void createBook_IsPresentInAllBooks() throws Exception {
        String title = "Title";
        String author = "Author";
        int numberPages = 234;
        double priceAmount = 34.5;

        Characteristics characteristics = new Characteristics(
                title,
                author,
                numberPages
        );
        Price price = new Price(priceAmount);

        // Save and get all books
        bookService.createNewBookEntry(characteristics, price);
        List<Book> allBooks = bookService.getAllBooks();

        assertEquals(1, allBooks.size());
        Book saved = allBooks.get(0);
        assertEquals(characteristics, saved.characteristics());
        assertEquals(price, saved.price());
    }

    @Test(expected = BookNotFoundException.class)
    public void getDetails_noBook_exception() throws Exception {
        bookService.getDetails(new BookId(1233L));
    }

    @Test
    public void getDetails() throws Exception {
        BookId id = persistFakeBookWithReview_returnId(12, validUsername);

        Book fromService = bookService.getDetails(id);

        assertFalse("Reviews should not be empty", fromService.getReviews().isEmpty());
    }

    private BookId persistFakeBookWithReview_returnId(int rating, UserId reviewerUsername) {
        // Persist book
        Book fakeBook = testUtils.makeRandomBook(BookId.NOT_ASSIGNED);
        BookId bookId = bookRepository.save(fakeBook);
        fakeBook.setId(bookId);

        // Add review
        Review review = testUtils.makeReview(bookId, rating, reviewerUsername.username());
        fakeBook.addReview(review);
        bookRepository.save(fakeBook);

        return bookId;
    }

    @Test(expected = IllegalReviewException.class)
    public void addReview_BookNotFound() throws Exception {
        bookService.addNewReview(new BookId(999L), new Rating(11), new UserId("patrick"));
    }

    @Test(expected = IllegalReviewException.class)
    public void addReview_existingReviewForUse() throws Exception {
        BookId id = persistFakeBookWithReview_returnId(12, validUsername);
        bookService.addNewReview(id, new Rating(33), validUsername);
    }

    @Test(expected = IllegalReviewException.class)
    public void addReview_noExistingUser() throws Exception {
        // Given: Book present in repo BUT no existing user
        Book fakeBook = testUtils.makeRandomBook(BookId.NOT_ASSIGNED);
        BookId bookId = bookRepository.save(fakeBook);

        // When & Then: Add review, throw exception
        bookService.addNewReview(bookId, new Rating(99), new UserId("inexisting user"));
    }

    @Test
    public void addReview() throws Exception {
        BookId id = persistFakeBookWithReview_returnId(34, validUsername);

        bookService.addNewReview(id, new Rating(99), otherValidUsername);

        Book fromService = bookService.getDetails(id);
        assertEquals(2, fromService.getReviews().size());
    }

    @Test(expected = BookNotFoundException.class)
    public void getAverageRating_BooKNotFound() throws Exception {
        bookService.getAverageRating(new BookId(999L));
    }

    @Test
    public void getAverageRating() throws Exception {
        BookId id = persistFakeBookWithReview_returnId(80, validUsername);
        bookService.addNewReview(id, new Rating(90), otherValidUsername);

        assertEquals(85, bookService.getAverageRating(id), 0);
    }
}