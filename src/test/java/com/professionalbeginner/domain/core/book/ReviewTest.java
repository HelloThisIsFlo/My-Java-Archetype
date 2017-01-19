package com.professionalbeginner.domain.core.book;

import com.google.common.testing.EqualsTester;
import com.professionalbeginner.domain.core.user.UserId;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Florian Kempenich
 */
public class ReviewTest {

    private BookId validBookId;
    private UserId validUserId;
    private Rating validRating;

    /*
     * Note to self:
     * Later when (if) implementing a review content: Possible to add without adding to constructor.
     * Just initialize with default value (NullObject pattern).
     *
     * Makes sense: a review doesn't necessarily have a text content.
     */

    @Before
    public void setUp() throws Exception {
        validBookId = new BookId(123L);
        validUserId = new UserId("Patrick");
        validRating = new Rating(3);
    }

    @Test
    public void testCreateInstance() throws Exception {
        assertValid(validBookId, validRating, validUserId);

        assertInvalid(null, validRating, validUserId);
        assertInvalid(validBookId, null, validUserId);
        assertInvalid(validBookId, validRating, null);
    }

    private void assertValid(BookId bookId, Rating rating, UserId userId) {
        new Review(bookId, userId, rating);
    }

    private void assertInvalid(BookId bookId, Rating rating, UserId userId) {
        try {
            new Review(bookId, userId, rating);
            fail("Should throw exception");
        } catch (NullPointerException e) {
            // expected
        }
    }

    @Test(expected = NullPointerException.class)
    public void setRatingNull() throws Exception {
        Review review = new Review(validBookId, validUserId, validRating);
        review.updateRating(null);
    }

    @Test
    public void testEquality() throws Exception {
        // two reviews are equals when their ids are equal

        Rating validRating2 = new Rating(45);
        UserId validUserId2 = new UserId("Florian");

        new EqualsTester()
                .addEqualityGroup(new Review(new BookId(567L), validUserId, validRating), new Review(new BookId(567L), validUserId, validRating))
                .addEqualityGroup(new Review(new BookId(567L), validUserId2, validRating2), new Review(new BookId(567L), validUserId2, validRating2))
                .testEquals();
    }

}