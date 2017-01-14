package com.professionalbeginner.domain.core.book;

import com.google.common.testing.EqualsTester;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author Kempenich Florian
 */
public class BookIdTest {

    @Test
    public void testEquality() throws Exception {
        new EqualsTester()
                .addEqualityGroup(new BookId("asdf"), new BookId("asdf"))
                .addEqualityGroup(new BookId("123"), new BookId("123"))
                .addEqualityGroup(new BookId("ASDF"), new BookId("ASDF")) //Case - sensible (not equal to line 1)
                .testEquals();
    }

    @Test(expected = NullPointerException.class)
    public void nullId() throws Exception {
        new BookId(null);
    }

    @Test
    public void emptyId() throws Exception {
        try {
            new BookId("");
            fail("Should throw exception");
        } catch (IllegalArgumentException e) {
            assertEquals("Id cannot be empty", e.getMessage());
        }
    }

    @Test
    public void idString() throws Exception {
        BookId id = new BookId("id-string");
        assertEquals("id-string", id.idString());
    }

}