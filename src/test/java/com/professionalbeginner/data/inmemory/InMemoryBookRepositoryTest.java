package com.professionalbeginner.data.inmemory;

import com.professionalbeginner.TestUtils;
import com.professionalbeginner.domain.core.book.Book;
import com.professionalbeginner.domain.core.book.BookId;
import com.professionalbeginner.domain.core.book.Review;
import com.professionalbeginner.domain.interfacelayer.repository.BookNotFoundException;
import com.professionalbeginner.domain.interfacelayer.repository.BookRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * @author Kempenich Florian
 */
public class InMemoryBookRepositoryTest {

    private BookRepository bookRepository;
    private TestUtils testUtils;
    private Random random;

    @Before
    public void setUp() throws Exception {
        random = new Random();
        bookRepository = new InMemoryBookRepository();
        testUtils = new TestUtils();
    }

    @Test(expected = BookNotFoundException.class)
    public void bookNotFound_throwException() throws Exception{
        bookRepository.findById(new BookId(333L), false);
    }

    @Test
    public void saveAndRetrieve() throws Exception {
        // Create and persist book
        Book book = testUtils.makeRandomBook(BookId.NOT_ASSIGNED);
        BookId savedId = bookRepository.save(book);
        book.setId(savedId);

        // Add reviews
        createRandomReviews(savedId).forEach(book::addReview);
        bookRepository.save(book);

        Book fromRepoWithReviews = bookRepository.findById(savedId, true);
        assertTrue("Books are not similar", areBooksSimilar(book, fromRepoWithReviews));

        Book fromRepoWithoutReviews = bookRepository.findById(savedId, false);
        assertTrue(
                "Books are not similar",
                areBooksSimilar_ignoreReviews(book, fromRepoWithoutReviews)
        );
        assertTrue("Review list shoudl be empty", fromRepoWithoutReviews.getReviews().isEmpty());
    }

    @Test
    public void saveWithExistingId_updates() throws Exception {
        Book book = testUtils.makeRandomBook(BookId.NOT_ASSIGNED);
        BookId id = bookRepository.save(book);

        Book updatedBook = testUtils.makeRandomBook(id);
        BookId updatedId = bookRepository.save(updatedBook);

        assertEquals(id, updatedId);

        Book fromRepo = bookRepository.findById(updatedId, true);
        assertEquals(updatedBook, fromRepo);
    }

    @Test
    public void saveAll_findAll() throws Exception {
        // Create and persist books
        Book book1 = testUtils.makeRandomBook(BookId.NOT_ASSIGNED);
        Book book2 = testUtils.makeRandomBook(BookId.NOT_ASSIGNED);
        Book book3 = testUtils.makeRandomBook(BookId.NOT_ASSIGNED);
        BookId savedId1 = bookRepository.save(book1);
        BookId savedId2 = bookRepository.save(book2);
        BookId savedId3 = bookRepository.save(book3);
        book1.setId(savedId1);
        book2.setId(savedId2);
        book3.setId(savedId3);
        List<Book> books = Arrays.asList(book1, book2, book3);

        // Add reviews to first book
        createRandomReviews(savedId1).forEach(book1::addReview);
        bookRepository.save(book1);


        List<Book> withReviews = bookRepository.findAll(true);
        List<Book> withoutReviews = bookRepository.findAll(false);

        assertEqualsIgnoreOrder(books, withReviews);
        books.forEach(
                book -> assertListContainsSimilarBook_ignoreReview(book, withoutReviews)
        );
        withoutReviews.forEach(this::assertReviewListEmtpy);
    }

    @Test
    public void idAreUnique() throws Exception {
        Book first = testUtils.makeRandomBook(BookId.NOT_ASSIGNED);
        Book second = testUtils.makeRandomBook(BookId.NOT_ASSIGNED);

        BookId firstId = bookRepository.save(first);
        BookId secondId = bookRepository.save(second);

        assertNotEquals(firstId, BookId.NOT_ASSIGNED);
        assertNotEquals(secondId, BookId.NOT_ASSIGNED);
        assertNotEquals(firstId, secondId);
    }

    @Test
    public void idIsUpdatedOnSavedBooks() throws Exception {
        Book toSave = testUtils.makeRandomBook(BookId.NOT_ASSIGNED);
        bookRepository.save(toSave);

        List<Book> fromRepo = bookRepository.findAll(false);
        assertEquals(1, fromRepo.size());
        Book savedBook = fromRepo.get(0);
        assertNotEquals(savedBook.id(), BookId.NOT_ASSIGNED);
    }

    private void assertReviewListEmtpy(Book book) {
        assertTrue("Review list should be empty", book.getReviews().isEmpty());
    }

    private <T> void assertEqualsIgnoreOrder(List<T> first, List<T> second) {
        String errorMessage = "List not equivalent: \n" +
                first + "\n" +
                second + "\n";
        assertTrue(errorMessage, first.containsAll(second));
        assertTrue(errorMessage, second.containsAll(first));
    }

    private List<Review> createRandomReviews(BookId bookId) {
        Review review1 = testUtils.makeRandomReview(bookId);
        Review review2 = testUtils.makeRandomReview(bookId);
        Review review3 = testUtils.makeRandomReview(bookId);
        return Arrays.asList(review1, review2, review3);
    }

    private void assertListContainsSimilarBook_ignoreReview(Book toCheck, List<Book> bookList) {
        boolean contains = bookList.stream()
                .anyMatch(book -> areBooksSimilar_ignoreReviews(toCheck, book));
        assertTrue("List does not contain book: " + toCheck, contains);
    }

    private boolean areBooksSimilar(Book toCheck, Book book) {
        return areBooksSimilar_ignoreReviews(toCheck, book)
                && toCheck.getReviews().containsAll(book.getReviews())
                && book.getReviews().containsAll(toCheck.getReviews());
    }

    private boolean areBooksSimilar_ignoreReviews(Book toCheck, Book book) {
        return toCheck.characteristics().title().equals(book.characteristics().title()) &&
                toCheck.characteristics().author().equals(book.characteristics().author()) &&
                toCheck.characteristics().numPages() == book.characteristics().numPages() &&
                Double.compare(toCheck.price().amount(), book.price().amount()) == 0;
    }

}