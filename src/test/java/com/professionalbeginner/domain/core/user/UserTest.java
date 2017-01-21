package com.professionalbeginner.domain.core.user;

import com.google.common.testing.EqualsTester;
import com.professionalbeginner.TestUtils;
import org.junit.Before;
import org.junit.Test;

import javax.validation.constraints.Null;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author Kempenich Florian
 */
public class UserTest {

    private UserId validId;
    private UserInfo validInfo;
    private TestUtils testUtils;

    @Before
    public void setUp() throws Exception {
        testUtils = new TestUtils();
        validId = new UserId("patrick887");
        validInfo = new UserInfo("Patrick", "Dupont", testUtils.defaultDate());
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
        UserInfo otherInfo = new UserInfo("Frank", "Herbert", testUtils.defaultDate().plusMonths(4));
        new EqualsTester()
                .addEqualityGroup(new User(validId, validInfo), new User(validId, validInfo))
                .addEqualityGroup(new User(otherId, otherInfo), new User(otherId, validInfo)) // Equality on id only
                .testEquals();
    }

    @Test
    public void userNotInLegalAge_throwException() throws Exception {
        // Given
        int illegalAge = 17;
        int legalAge = 18;

        LocalDate illegalBirthDate = LocalDate.now().minusYears(illegalAge);
        LocalDate legalBirthDate = LocalDate.now().minusYears(legalAge);

        // When
        UserInfo illegalInfo = new UserInfo("asdfdd", "sdasdf", illegalBirthDate);
        try {
            new User(UserId.NULL, illegalInfo);
            fail("Should throw exception");
        }

        // Then
        catch (IllegalUserException e) {
            assertEquals("User not in legal age: " + illegalInfo, e.getMessage());
            assertEquals(illegalInfo, e.getUserInfo());
        }
        new User(UserId.NULL, new UserInfo("asdf", "asdf", legalBirthDate)); // no exception
    }
}