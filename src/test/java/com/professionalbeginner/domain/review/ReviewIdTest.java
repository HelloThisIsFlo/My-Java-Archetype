package com.professionalbeginner.domain.review;

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
    public void emptyId() throws Exception {
        try {
            new ReviewId("");
            fail("Should throw exception");
        } catch (Exception e) {
            assertEquals("Id cannot be empty", e.getMessage());
        }
    }

    @Test
    public void testEquality() throws Exception {
        new EqualsTester()
                .addEqualityGroup(new ReviewId("asdf"), new ReviewId("asdf"))
                .addEqualityGroup(new ReviewId("dd"), new ReviewId("dd"))
                .addEqualityGroup(new ReviewId("DD"), new ReviewId("DD"))
                .testEquals();
    }

}