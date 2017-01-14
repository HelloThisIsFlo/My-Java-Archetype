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
                .addEqualityGroup(new BookId(12L), new BookId(12L))
                .addEqualityGroup(new BookId(123L), new BookId(123L))
                .testEquals();
    }

    @Test(expected = NullPointerException.class)
    public void nullId() throws Exception {
        new BookId(null);
    }

    @Test
    public void negativeId() throws Exception {
        try {
            new BookId(-1L);
            fail("Should throw exception");
        } catch (IllegalArgumentException e) {
            assertEquals("Id cannot be <= 0", e.getMessage());
        }
        try {
            new BookId(0L);
            fail("Should throw exception");
        } catch (IllegalArgumentException e) {
            assertEquals("Id cannot be <= 0", e.getMessage());
        }
    }

    @Test
    public void test_idLong() throws Exception {
        BookId toTest = new BookId(456L);
        assertEquals(456L, toTest.idLong());
    }
}