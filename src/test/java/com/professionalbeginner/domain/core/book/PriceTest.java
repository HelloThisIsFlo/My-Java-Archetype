package com.professionalbeginner.domain.core.book;

import com.google.common.testing.EqualsTester;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author Kempenich Florian
 */
public class PriceTest {

    @Test
    public void testCreate() throws Exception {
        new Price(12);
        new Price(0);

        try {
            new Price(-1);
            fail("Should throw exception");
        } catch (IllegalArgumentException e) {
            assertEquals("Amount cannot be < 0", e.getMessage());
        }
    }


    /*
     * Note to self:
     * I wanted to implement a delta in the 'equals' definition.
     *
     * But poses a problem: if 11.05 == 11 but not 11.1 what about the fact that: 11 == 11.05 == 11.1 ??
     * So cannot create equivalencies with delta. Otherwise any value is equivalent to any other.
     *
     */
    @Test
    public void testEquality() throws Exception {
        new EqualsTester()
                .addEqualityGroup(new Price(12), new Price(12))
                .addEqualityGroup(new Price(11), new Price(11))
                .addEqualityGroup(new Price(11.000000000000001), new Price(11.000000000000001))
                .testEquals();
    }

    @Test
    public void testAmount() throws Exception {
        assertEquals(12, new Price(12).amount(), 0);

    }

}