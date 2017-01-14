package com.professionalbeginner.domain.book;

import com.google.common.testing.EqualsTester;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author Kempenich Florian
 */
public class BookTest {

    private Characteristics validCharact;
    private Price validPrice;

    @Before
    public void setUp() throws Exception {
        validCharact = new Characteristics("title", "author", 34);
        validPrice = new Price(530.1);
    }

    @Test
    public void testNewInstance() throws Exception {
        assertValid(validCharact, validPrice);

        assertInvalid(null, validPrice);
        assertInvalid(validCharact,null);
    }

    private void assertValid(Characteristics characteristics, Price price) {
        new Book(characteristics, price);
    }

    private void assertInvalid(Characteristics characteristics, Price price) {
        try {
            new Book(characteristics, price);
            fail("Should throw exception");
        } catch (NullPointerException e) {
            // expected
        }
    }

    @Test(expected = NullPointerException.class)
    public void setNullId() throws Exception {
        Book book = new Book(validCharact, validPrice);
        book.setId(null);
    }

    @Test
    public void testAttributes() throws Exception {
        Book book = new Book(
                new Characteristics("Best Book Ever", "Best author", 777),
                new Price(999)
        );

        assertEquals(
                new Characteristics("Best Book Ever", "Best author", 777),
                book.characteristics()
        );
        assertEquals(new Price(999), book.price());
    }

    @Test
    public void testEquality() throws Exception {

        Characteristics validCharact2 = new Characteristics("other title", "other author", 21);
        Price validPrice2 = new Price(120.23);

        BookId id1 = new BookId("id1");
        BookId id2 = new BookId("id2");

        Book bookWithId1 = new Book(validCharact, validPrice);
        Book differentBookWithId1 = new Book(validCharact2, validPrice2);
        Book bookWithId2 = new Book(validCharact, validPrice);

        bookWithId1.setId(id1);
        differentBookWithId1.setId(id1);
        bookWithId2.setId(id2);


        new EqualsTester()
                .addEqualityGroup(bookWithId1, differentBookWithId1)
                .addEqualityGroup(bookWithId2, bookWithId2)
                .testEquals();

    }
}