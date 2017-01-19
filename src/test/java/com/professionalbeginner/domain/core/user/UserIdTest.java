package com.professionalbeginner.domain.core.user;

import com.google.common.testing.EqualsTester;
import org.junit.Test;

import static org.junit.Assert.fail;

/**
 * @author Kempenich Florian
 */
public class UserIdTest {

    @Test(expected = NullPointerException.class)
    public void testValidity() throws Exception {
        assertValid("florian");
        assertValid("P");

        assertInvalid(null);
        assertInvalid("");
    }

    @Test
    public void testEquality() throws Exception {
        new EqualsTester()
                .addEqualityGroup(new UserId("patrick"), new UserId("patrick"))
                .addEqualityGroup(new UserId("florian"), new UserId("florian"))
                .testEquals();

    }

    private void assertValid(String username) {
        new UserId(username);
    }

    private void assertInvalid(String username) {
        try {
            new UserId(username);
            fail("Should throw exception");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

}