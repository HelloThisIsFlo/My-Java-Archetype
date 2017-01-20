package com.professionalbeginner.domain.core.user;

import com.google.common.testing.EqualsTester;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.Date;

import static org.junit.Assert.fail;

/**
 * @author Kempenich Florian
 */
public class UserTest {

    private UserId validId;
    private UserInfo validInfo;

    @Before
    public void setUp() throws Exception {
        validId = new UserId("patrick887");
        validInfo = new UserInfo("Patrick", "Dupont", Date.from(Instant.EPOCH));
    }

    @Test
    public void testNewInstance() throws Exception {
        assertValid(validId, validInfo);
        assertInvalid(null, validInfo);
        assertInvalid(validId, null);
    }

    private void assertValid(UserId usename, UserInfo info) {
        new User(usename, info);
    }

    private void assertInvalid(UserId usename, UserInfo info) {
        try {
            new User(usename, info);
            fail("Should throw exception");
        } catch (NullPointerException e) {
            // expected
        }
    }

    @Test
    public void testEquality() throws Exception {
        UserId otherId = new UserId("Franky3939");
        UserInfo otherInfo = new UserInfo("Frank", "Herbert", Date.from(Instant.EPOCH));
        new EqualsTester()
                .addEqualityGroup(new User(validId, validInfo), new User(validId, validInfo))
                .addEqualityGroup(new User(otherId, otherInfo), new User(otherId, validInfo)) // Equality on id only
                .testEquals();
    }
}