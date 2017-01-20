package com.professionalbeginner.domain.core.user;

import com.google.common.testing.EqualsTester;
import org.junit.Test;

import static org.junit.Assert.fail;

/**
 * @author Kempenich Florian
 */
public class UserTest {

    @Test
    public void testNewInstance() throws Exception {
        assertValid(new UserId("patrick"));
        assertInvalid(null);
    }

    private void assertValid(UserId usename) {
        new User(usename);
    }

    private void assertInvalid(UserId usename) {
        try {
            new User(usename);
            fail("Should throw exception");
        } catch (NullPointerException e) {
            // expected
        }
    }

    @Test
    public void testEquality() throws Exception {
        UserId username1 = new UserId("patrick");
        UserId username2 = new UserId("frank");
        new EqualsTester()
                .addEqualityGroup(new User(username1), new User(username1))
                .addEqualityGroup(new User(username2), new User(username2))
                .testEquals();
    }
}