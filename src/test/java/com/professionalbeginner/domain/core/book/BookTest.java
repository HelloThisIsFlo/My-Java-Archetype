package com.professionalbeginner.domain.core.book;

import com.google.common.testing.EqualsTester;
import com.professionalbeginner.TestUtils;
import com.professionalbeginner.domain.core.review.IllegalReviewException;
import com.professionalbeginner.domain.core.review.Review;
import com.professionalbeginner.domain.core.review.User;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Kempenich Florian
 */
public class BookTest {

    private Characteristics validCharact;
    private Price validPrice;
    private User validUser;
    private Book validBook;

    private TestUtils testUtils = new TestUtils();

    @Before
    public void setUp() throws Exception {
        validCharact = new Characteristics("title", "author", 34);
        validPrice = new Price(530.1);
        validBook = new Book(
                new Characteristics("Best Book Ever", "Best author", 777),
                new Price(999)
        );
        BookId id = new BookId("book-id");
        validBook.setId(id);
        validUser = new User("pedro");
    }

    @Test
    public void testNewInstance() throws Exception {
        assertValid(validCharact, validPrice);

        assertInvalid(null, validPrice);
        assertInvalid(validCharact,null);
    }

    private void assertValid(Characteristics characteristics, Price price) {
        new Book(characteristics, price);
    }

    private void assertInvalid(Characteristics characteristics, Price price) {
        try {
            new Book(characteristics, price);
            fail("Should throw exception");
        } catch (NullPointerException e) {
            // expected
        }
    }

    @Test(expected = NullPointerException.class)
    public void setNullId() throws Exception {
        Book book = new Book(validCharact, validPrice);
        book.setId(null);
    }

    @Test
    public void testAttributes() throws Exception {
        Book book = new Book(
                new Characteristics("Best Book Ever", "Best author", 777),
                new Price(999)
        );

        assertEquals(
                new Characteristics("Best Book Ever", "Best author", 777),
                book.characteristics()
        );
        assertEquals(new Price(999), book.price());
        assertEquals(Collections.emptyList(), book.getReviews());
    }

    @Test
    public void testEquality() throws Exception {

        Characteristics validCharact2 = new Characteristics("other title", "other author", 21);
        Price validPrice2 = new Price(120.23);

        BookId id1 = new BookId("id1");
        BookId id2 = new BookId("id2");

        Book bookWithId1 = new Book(validCharact, validPrice);
        Book differentBookWithId1 = new Book(validCharact2, validPrice2);
        Book bookWithId2 = new Book(validCharact, validPrice);

        bookWithId1.setId(id1);
        differentBookWithId1.setId(id1);
        bookWithId2.setId(id2);


        new EqualsTester()
                .addEqualityGroup(bookWithId1, differentBookWithId1)
                .addEqualityGroup(bookWithId2, bookWithId2)
                .testEquals();
    }

    @Test
    public void add_and_get_reviews() throws Exception {
        assertEquals(Collections.emptyList(), validBook.getReviews());

        BookId id = validBook.id();
        Review review1 = testUtils.makeRandomReview(id);
        Review review2 = testUtils.makeRandomReview(id);
        Review review3 = testUtils.makeRandomReview(id);

        List<Review> expected = new ArrayList<>(3);
        expected.add(review1);
        expected.add(review2);
        expected.add(review3);

        validBook.addReview(review1);
        validBook.addReview(review2);
        validBook.addReview(review3);

        assertEquals(expected, validBook.getReviews());
    }

    @Test
    public void addReview_wrongBookId_throwException() throws Exception {
        BookId wrongId = new BookId("wrong");
        assertNotEquals(validBook.id(), wrongId);

        Review reviewWithWrongId = testUtils.makeRandomReview(wrongId);

        try {
            validBook.addReview(reviewWithWrongId);
            fail("Should throw exception");
        } catch (IllegalReviewException e) {
            assertEquals("Wrong Book Id", e.getMessage());
            assertEquals(reviewWithWrongId.getId(), e.getReview().getId());
        }
    }

    @Test
    public void addReview_existingReviewForUser_throwException() throws Exception {
        Review initialReview = testUtils.makeRandomReview(validBook.id(), validUser);
        validBook.addReview(initialReview);

        Review reviewWithExistingUser = testUtils.makeRandomReview(validBook.id(), validUser);

        try {
            validBook.addReview(reviewWithExistingUser);
            fail("Should throw exception");
        } catch (IllegalReviewException e) {
            assertEquals("Review existing for this book for this user", e.getMessage());
            assertEquals(initialReview.getId(), e.getReview().getId());
        }
    }

}