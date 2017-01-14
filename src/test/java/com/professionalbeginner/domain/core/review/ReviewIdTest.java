package com.professionalbeginner.domain.core.review;

import com.google.common.testing.EqualsTester;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author Kempenich Florian
 */
public class ReviewIdTest {

    @Test(expected = NullPointerException.class)
    public void nullId() throws Exception {
        new ReviewId(null);
    }

    @Test
    public void negativeId() throws Exception {
        try {
            new ReviewId(-1L);
            fail("Should throw exception");
        } catch (IllegalArgumentException e) {
            assertEquals("Id cannot be <= 0", e.getMessage());
        }
        try {
            new ReviewId(0L);
            fail("Should throw exception");
        } catch (IllegalArgumentException e) {
            assertEquals("Id cannot be <= 0", e.getMessage());
        }
    }

    @Test
    public void test_idLong() throws Exception {
        ReviewId toTest = new ReviewId(456L);
        assertEquals(456L, toTest.idLong());

    }

    @Test
    public void testEquality() throws Exception {
        new EqualsTester()
                .addEqualityGroup(new ReviewId(123L), new ReviewId(123L))
                .addEqualityGroup(new ReviewId(677L), new ReviewId(677L))
                .testEquals();
    }

}