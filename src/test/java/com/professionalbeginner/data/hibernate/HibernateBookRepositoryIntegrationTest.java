package com.professionalbeginner.data.hibernate;

import com.professionalbeginner.TestUtils;
import com.professionalbeginner.domain.core.book.Book;
import com.professionalbeginner.domain.core.book.BookId;
import com.professionalbeginner.domain.core.book.Price;
import com.professionalbeginner.domain.core.review.Review;
import com.professionalbeginner.domain.core.review.ReviewId;
import com.professionalbeginner.domain.interfacelayer.repository.BookRepository;
import com.professionalbeginner.domain.interfacelayer.repository.ReviewRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author Kempenich Florian
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-tests")
public class HibernateBookRepositoryIntegrationTest {

    // TODO: 12/10/2016 Also implement integration tests on service, or even controller

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private TestUtils testUtils;

    @Test
    public void saveAndFind_withoutReviews() throws Exception {
        Book toSave = testUtils.makeDefaultBook(BookId.NOT_ASSIGNED);
        assertEquals(BookId.NOT_ASSIGNED, toSave.id());

        BookId savedId = bookRepository.save(toSave);
        assertNotEquals(BookId.NOT_ASSIGNED, savedId); //Check that repository returns a correct id

        Book fromRepo = bookRepository.findById(savedId, false);

        assertEquals(savedId, fromRepo.id());
        assertTrue(areBooksSimilar_ignoreReviews(toSave, fromRepo));
    }

    @Test
    public void saveWithExistingId_updatesExisting() throws Exception {
        // Save book and get id
        Book toSave = testUtils.makeDefaultBook(BookId.NOT_ASSIGNED);
        BookId savedId = bookRepository.save(toSave);
        toSave.setId(savedId);

        // Change state of book
        Price originalPrice = toSave.price();
        Price newPrice = new Price(originalPrice.amount() / 2);
        toSave.updatePrice(newPrice);

        // Get from repo before and after update
        Book fromRepo = bookRepository.findById(savedId, false);
        bookRepository.save(toSave);
        Book fromRepoAfterUpdate = bookRepository.findById(savedId, false);

        // Assert result
        assertEquals(originalPrice, fromRepo.price());
        assertEquals(newPrice, fromRepoAfterUpdate.price());
    }

    @Test
    public void saveAndFind_withReviews() throws Exception {
        // Get id
        Book toSave = testUtils.makeDefaultBook(BookId.NOT_ASSIGNED);
        BookId savedId = bookRepository.save(toSave);
        toSave.setId(savedId);

        // Create and persist review
        Review review1 = testUtils.makeReview(ReviewId.NOT_ASSIGNED, savedId, 44, "mark");
        Review review2 = testUtils.makeReview(ReviewId.NOT_ASSIGNED, savedId, 30, "bob");
        Review review3 = testUtils.makeReview(ReviewId.NOT_ASSIGNED, savedId, 98, "frank");
        ReviewId id1 = reviewRepository.save(review1);
        ReviewId id2 = reviewRepository.save(review2);
        ReviewId id3 = reviewRepository.save(review3);
        review1.setId(id1);
        review2.setId(id2);
        review3.setId(id3);

        // Add reviews and update version on repo
        toSave.addReview(review1);
        toSave.addReview(review2);
        toSave.addReview(review3);
        bookRepository.save(toSave);

        Book fromRepo = bookRepository.findById(savedId, true);

        assertEquals(toSave.getReviews(), fromRepo.getReviews());
        assertTrue(areBooksSimilar_ignoreReviews(toSave, fromRepo));
    }

    private boolean areBooksSimilar_ignoreReviews(Book toCheck, Book book) {
        return toCheck.characteristics().title().equals(book.characteristics().title()) &&
                toCheck.characteristics().author().equals(book.characteristics().author()) &&
                toCheck.characteristics().numPages() == book.characteristics().numPages() &&
                Double.compare(toCheck.price().amount(), book.price().amount()) == 0;
    }










//    @Test
//    public void saveMultiple_findAll() throws Exception {
//        Book book1 = testUtils.makeBook("id-1", "title", "author", 39, 53.12);
//        Book book2 = testUtils.makeBook("id-2", "Learn portuguese", "Alexandra", 321, 124);
//        Book book3 = testUtils.makeBook("id-3", "Third book", "Philip", 665, 53.2);
//
//        bookRepository.save(book1);
//        bookRepository.save(book2);
//        bookRepository.save(book3);
//
//        List<Book> fromRepo = bookRepository.findAll(false);
//
//        assertEquals(3, fromRepo.size());
//        assertListContainsSimilarBook(book1, fromRepo);
//        assertListContainsSimilarBook(book2, fromRepo);
//        assertListContainsSimilarBook(book3, fromRepo);
//    }
//
//    private void assertListContainsSimilarBook(Book toCheck, List<Book> bookList) {
//        boolean contains = bookList.stream()
//                .anyMatch(book -> areBooksSimilar(toCheck, book));
//
//        assertTrue("List does not contain book: " + toCheck, contains);
//    }
//

}