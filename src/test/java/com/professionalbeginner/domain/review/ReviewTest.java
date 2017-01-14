package com.professionalbeginner.domain.review;

import com.google.common.testing.EqualsTester;
import com.professionalbeginner.domain.book.BookId;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.fail;

/**
 * @author Kempenich Florian
 */
public class ReviewTest {

    private ReviewId validId;
    private BookId validBookId;
    private User validUser;
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
        validId = new ReviewId("abc");
        validBookId = new BookId("book-id");
        validUser = new User("Patrick");
        validRating = new Rating(3);
    }

    @Test
    public void testCreateInstance() throws Exception {
        assertValid(validId, validBookId, validRating, validUser);

        assertInvalid(null, validBookId, validRating, validUser);
        assertInvalid(validId, null, validRating, validUser);
        assertInvalid(validId, validBookId, null, validUser);
        assertInvalid(validId, validBookId, validRating, null);
    }

    private void assertValid(ReviewId reviewId, BookId bookId, Rating rating, User user) {
        new Review(reviewId, bookId, user, rating);
    }

    private void assertInvalid(ReviewId reviewId, BookId bookId, Rating rating, User user) {
        try {
            new Review(reviewId, bookId, user, rating);
            fail("Should throw exception");
        } catch (NullPointerException e) {
            // expected
        }
    }

    @Test(expected = NullPointerException.class)
    public void setRatingNull() throws Exception {
        Review review = new Review(validId, validBookId, validUser, validRating);
        review.updateRating(null);
    }

    @Test
    public void testEquality() throws Exception {
        // two reviews are equals when their ids are equal

        Rating validRating2 = new Rating(45);
        User validUser2 = new User("Florian");

        ReviewId id1 = new ReviewId("first-id");
        ReviewId id2 = new ReviewId("second-id");
        ReviewId id3 = new ReviewId("third-id");

        new EqualsTester()
                .addEqualityGroup(new Review(id1, new BookId("book-id"), validUser, validRating), new Review(id1, new BookId("book-id"), validUser, validRating))
                .addEqualityGroup(new Review(id2, new BookId("book-id"), validUser, validRating), new Review(id2, new BookId("book-id"), validUser, validRating))
                .addEqualityGroup(new Review(id3, new BookId("book-id"), validUser2, validRating2), new Review(id3, new BookId("book-id"), validUser, validRating))
                .testEquals();
    }

}