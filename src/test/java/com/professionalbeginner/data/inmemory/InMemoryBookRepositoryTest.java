package com.professionalbeginner.data.inmemory;

import com.professionalbeginner.TestUtils;
import com.professionalbeginner.domain.core.book.Book;
import com.professionalbeginner.domain.core.book.BookId;
import com.professionalbeginner.domain.core.book.Price;
import com.professionalbeginner.domain.core.review.Review;
import com.professionalbeginner.domain.core.review.ReviewId;
import com.professionalbeginner.domain.interfacelayer.repository.BookRepository;
import com.professionalbeginner.domain.interfacelayer.repository.ReviewRepository;
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
    private ReviewRepository reviewRepository;
    private TestUtils testUtils;
    private Random random;

    @Before
    public void setUp() throws Exception {
        bookRepository = new InMemoryBookRepository();
        reviewRepository = null;
        testUtils = new TestUtils();
    }

    @Test
    public void saveAndRetrieve() throws Exception {
        Book book = testUtils.makeRandomBook(BookId.NOT_ASSIGNED);

        BookId savedId = bookRepository.save(book);
        book.setId(savedId);

        Book fromRepoWithReviews = bookRepository.findById(savedId, true);
        assertTrue("Books are not similar", areBooksSimilar(book, fromRepoWithReviews));

        Book fromRepoWithoutReviews = bookRepository.findById(savedId, false);
        assertTrue(
                "Books are not similar",
                areBooksSimilar_ignoreReviews(book, fromRepoWithoutReviews)
        );
        assertTrue("Review list shoudl be empty", fromRepoWithoutReviews.getReviews().isEmpty());
    }

    private List<Review> createRandomReviewWithRadomId(BookId bookId) {
        Review review1 = testUtils.makeRandomReview(randomId(), bookId);
        Review review2 = testUtils.makeRandomReview(randomId(), bookId);
        Review review3 = testUtils.makeRandomReview(randomId(), bookId);
        ReviewId id1 = reviewRepository.save(review1);
        ReviewId id2 = reviewRepository.save(review2);
        ReviewId id3 = reviewRepository.save(review3);
        review1.setId(id1);
        review2.setId(id2);
        review3.setId(id3);

        return Arrays.asList(review1, review2, review3);
    }

    private ReviewId randomId() {
        long rand = random.nextInt(999) + 1;
        return new ReviewId(rand);
    }

    private void assertListContainsSimilarBook_withReviews(Book toCheck, List<Book> bookList) {
        boolean contains = bookList.stream()
                .anyMatch(book -> areBooksSimilar(toCheck, book));
        assertTrue("List does not contain book: " + toCheck, contains);
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