package com.professionalbeginner.domain.core.book;

import com.google.common.testing.EqualsTester;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author Kempenich Florian
 */
public class CharacteristicsTest {

    @Test
    public void testCreate() throws Exception {
        assertValid("title", "author", 23);
        assertValid("The Big Monkey", "Patrick Stein", 1);

        assertInvalid(null, "author", 23);
        assertInvalid("", "author", 23);
        assertInvalid("title", null, 23);
        assertInvalid("title", "", 23);
        // Page count must be > 0
        assertInvalid("title", "author", 0);
        assertInvalid("title", "author", -1);
    }

    private void assertValid(String title, String author, int numPages) {
        new Characteristics(title, author, numPages);
    }

    private void assertInvalid(String title, String author, int numPages) {
        try {
            new Characteristics(title, author, numPages);
            fail("Should throw exception");
        } catch (IllegalArgumentException | NullPointerException e) {
            // expected
        }
    }

    @Test
    public void testEquality() throws Exception {
        new EqualsTester().addEqualityGroup(
                new Characteristics("title", "author", 12),
                new Characteristics("title", "author", 12)
        ).addEqualityGroup(
                new Characteristics("Hello", "Mark", 44),
                new Characteristics("Hello", "Mark", 44)
        ).addEqualityGroup(
                new Characteristics("a", "b", 1),
                new Characteristics("a", "b", 1)
        ).addEqualityGroup(
                new Characteristics("ab", "b", 1),
                new Characteristics("ab", "b", 1)
        ).addEqualityGroup(
                new Characteristics("a", "bb", 1),
                new Characteristics("a", "bb", 1)
        ).addEqualityGroup(
                new Characteristics("a", "b", 2),
                new Characteristics("a", "b", 2)
        ).testEquals();
    }

    @Test
    public void testFields() throws Exception {
        Characteristics characteristics = new Characteristics("title", "author", 11);
        assertEquals("title", characteristics.title());
        assertEquals("author", characteristics.author());
        assertEquals(11, characteristics.numPages());
    }

}