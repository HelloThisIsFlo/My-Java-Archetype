package com.professionalbeginner.domain.review;

import com.google.common.testing.EqualsTester;
import org.junit.Test;

import static org.junit.Assert.fail;

/**
 * @author Kempenich Florian
 */
public class UserTest {

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
                .addEqualityGroup(new User("patrick"), new User("patrick"))
                .addEqualityGroup(new User("florian"), new User("florian"))
                .testEquals();

    }

    private void assertValid(String username) {
        new User(username);
    }

    private void assertInvalid(String username) {
        try {
            new User(username);
            fail("Should throw exception");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

}