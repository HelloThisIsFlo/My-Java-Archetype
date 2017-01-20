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
public class UserInfoTest {

    private Date validDate;
    private String validFirstName;
    private String validLastName;

    // TODO: 1/19/2017 Add test on date
    // Here: tests if date not in future or like 150 years in past
    // On User: tests if user > 18 yo
    @Before
    public void setUp() throws Exception {
        validDate = Date.from(Instant.now()); //todo: Update for something less random
        validFirstName = "Patrick";
        validLastName = "Smith";
    }

    @Test
    public void testNewInstance() throws Exception {
        assertValid(validFirstName, validLastName, validDate);

        assertInvalid(null, validLastName, validDate);
        assertInvalid(validFirstName, null, validDate);
        assertInvalid(validFirstName, validLastName, null);
    }

    private void assertValid(String firstName, String lastName, Date birthdate) {
        new UserInfo(firstName, lastName, birthdate);
    }

    private void assertInvalid(String firstName, String lastName, Date birthdate) {
        try {
            new UserInfo(firstName, lastName, birthdate);
            fail("Should throw exception");
        } catch (NullPointerException e) {
            // expected
        }
    }

    @Test
    public void testEquality() throws Exception {
        String anotherFirstName = "Another first name";
        String anotherLastName = "Another last name";
        Date anotherDate = Date.from(Instant.EPOCH);
        new EqualsTester()
                .addEqualityGroup(new UserInfo(validFirstName, validLastName, validDate), new UserInfo(validFirstName, validLastName, validDate))
                .addEqualityGroup(new UserInfo(anotherFirstName, validLastName, validDate), new UserInfo(anotherFirstName, validLastName, validDate))
                .addEqualityGroup(new UserInfo(anotherFirstName, anotherLastName, anotherDate), new UserInfo(anotherFirstName, anotherLastName, anotherDate))
                .testEquals();
    }

}