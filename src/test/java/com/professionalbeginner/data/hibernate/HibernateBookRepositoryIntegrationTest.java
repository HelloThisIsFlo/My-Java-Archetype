package com.professionalbeginner.data.hibernate;

import com.google.common.collect.Iterables;
import com.professionalbeginner.TestUtils;
import com.professionalbeginner.domain.core.book.Book;
import com.professionalbeginner.domain.core.book.BookId;
import com.professionalbeginner.domain.core.book.Price;
import com.professionalbeginner.domain.interfacelayer.repository.BookRepository;
import org.junit.Ignore;
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
    public void saveWithExistingId_updatesExistingReview() throws Exception {
        // Save book and get id
        Book toSave = testUtils.makeDefaultBook(BookId.NOT_ASSIGNED);
        BookId savedId = bookRepository.save(toSave);
        toSave.setId(savedId);

        // Change state of book
        Price originalPrice = toSave.price();
        Price newPrice = new Price(originalPrice.amount() / 2);
        toSave.updatePrice(newPrice);

        Book fromRepo = bookRepository.findById(savedId, false);
        bookRepository.save(toSave);
        Book fromRepoAfterUpdate = bookRepository.findById(savedId, false);

        assertEquals(originalPrice, fromRepo.price());
        assertEquals(newPrice, fromRepoAfterUpdate.price());
    }

    @Test
    @Ignore
    public void saveAndFind_withReviews() throws Exception {
        // Get id
        Book toSave = testUtils.makeDefaultBook(BookId.NOT_ASSIGNED);
        BookId savedId = bookRepository.save(toSave);
        toSave.setId(savedId);

        // Add reviews and update version on repo
        fail("uncomment & fix");
//        toSave.addReview(testUtils.makeRandomReview(savedId));
//        toSave.addReview(testUtils.makeRandomReview(savedId));
//        toSave.addReview(testUtils.makeRandomReview(savedId));
//        bookRepository.save(toSave);
//
//        Book fromRepo = bookRepository.findById(savedId, true);
//
//        assertTrue(areBooksSimilar(toSave, fromRepo));
    }

    private boolean areBooksSimilar(Book toCheck, Book book) {
        return areBooksSimilar_ignoreReviews(toCheck, book)
                && Iterables.elementsEqual(toCheck.getReviews(), book.getReviews());
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