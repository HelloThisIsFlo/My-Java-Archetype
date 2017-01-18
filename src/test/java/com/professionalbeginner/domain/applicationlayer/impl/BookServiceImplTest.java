package com.professionalbeginner.domain.applicationlayer.impl;

import com.professionalbeginner.TestUtils;
import com.professionalbeginner.data.inmemory.InMemoryBookRepository;
import com.professionalbeginner.domain.applicationlayer.BookService;
import com.professionalbeginner.domain.core.book.*;
import com.professionalbeginner.domain.core.review.IllegalReviewException;
import com.professionalbeginner.domain.core.review.Rating;
import com.professionalbeginner.domain.core.review.User;
import com.professionalbeginner.domain.interfacelayer.repository.BookNotFoundException;
import com.professionalbeginner.domain.interfacelayer.repository.BookRepository;
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
    private BookService bookService;

    private TestUtils testUtils;

    @Before
    public void setUp() throws Exception {
        testUtils = new TestUtils();

        bookRepository = new InMemoryBookRepository();
        StatisticsContract statistics = new StatisticsContractImpl();
        bookService = new BookServiceImpl(bookRepository, statistics);
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
        BookId id = addFakeBookWithReviewToRepo_andGetId(12, "mark");

        Book fromService = bookService.getDetails(id);

        assertFalse("Reviews should not be empty", fromService.getReviews().isEmpty());
    }

    private BookId addFakeBookWithReviewToRepo_andGetId(int rating, String reviewer) {
        Book fakeBook = testUtils.makeRandomBook(BookId.NOT_ASSIGNED);
        BookId bookId = bookRepository.save(fakeBook);
        fakeBook.setId(bookId);
        Review review = testUtils.makeReview(bookId, rating, reviewer);
        fakeBook.addReview(review);
        bookRepository.save(fakeBook);
        return bookId;
    }

    @Test(expected = IllegalReviewException.class)
    public void addReview_BookNotFound() throws Exception {
        bookService.addNewReview(new BookId(999L), new Rating(11), new User("patrick"));
    }

    @Test(expected = IllegalReviewException.class)
    public void addReview_existingReviewForUse() throws Exception {
        BookId id = addFakeBookWithReviewToRepo_andGetId(12, "frank");
        bookService.addNewReview(id, new Rating(33), new User("frank"));
    }

    @Test
    public void addReview() throws Exception {
        BookId id = addFakeBookWithReviewToRepo_andGetId(34, "frank");

        bookService.addNewReview(id, new Rating(99), new User("Mark"));

        Book fromService = bookService.getDetails(id);
        assertEquals(2, fromService.getReviews().size());
    }

    @Test(expected = BookNotFoundException.class)
    public void getAverageRating_BooKNotFound() throws Exception {
        bookService.getAverageRating(new BookId(999L));
    }

    @Test
    public void getAverageRating() throws Exception {
        BookId id = addFakeBookWithReviewToRepo_andGetId(80, "frank");
        bookService.addNewReview(id, new Rating(90), new User("mark"));

        assertEquals(85, bookService.getAverageRating(id), 0);
    }
}