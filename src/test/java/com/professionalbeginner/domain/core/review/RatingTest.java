package com.professionalbeginner.domain.core.review;

import com.google.common.testing.EqualsTester;
import com.professionalbeginner.domain.core.book.Rating;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author Kempenich Florian
 */
public class RatingTest {

    @Test
    public void createNewInstance_testValidation() throws Exception {
        assertValid(4);
        assertValid(99);
        assertValid(100);
        assertValid(0);

        assertInvalid(-1);
        assertInvalid(101);
    }

    @Test
    public void testValue() throws Exception {
        Rating rating = new Rating(4);
        assertEquals(4, rating.value());
    }

    /*
     * Note: Also tests hashcode :D :D
     */
    @Test
    public void testEquals() throws Exception {
        new EqualsTester()
                .addEqualityGroup(new Rating(3), new Rating(3))
                .addEqualityGroup(new Rating(99), new Rating(99))
                .testEquals();
    }

    private void assertValid(int rating) {
        new Rating(rating);
    }

    private void assertInvalid(int rating) {
        try {
            new Rating(rating);
            fail("Expecting an exception");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

}