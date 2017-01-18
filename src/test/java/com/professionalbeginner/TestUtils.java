package com.professionalbeginner;

import com.professionalbeginner.domain.core.book.*;
import com.professionalbeginner.domain.core.review.Rating;
import com.professionalbeginner.domain.core.review.User;
import com.professionalbeginner._other.spring.IntegrationTests;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @author Kempenich Florian
 */
@Component
@IntegrationTests
public class TestUtils {

    private final Random random;

    public TestUtils() {
        random = new Random();
    }


    public Book makeBook(BookId id, String title, String author, int numPage, double price) {
        Book book = new Book(
                new Characteristics(title, author, numPage),
                new Price(price)
        );
        book.setId(id);
        return book;
    }

    public Book makeRandomBook(BookId id) {
        return makeBook(
                id,
                randomString(),
                randomString(),
                randomPositiveInt(300),
                randomPositiveInt(180) / 2.0
        );
    }

    public Review makeReview(BookId bookId, int rating, String reviewerName) {
        return new Review(bookId, new User(reviewerName), new Rating(rating));
    }

    public Review makeRandomReview(BookId bookId) {
        return makeReview(bookId, randomRating(), randomString());
    }

    private int randomRating() {
        return randomPositiveInt(99);
    }
    private int randomPositiveInt(int bound) {
        return random.nextInt(bound) + 1;
    }

    private String randomString() {
        return Long.toString(random.nextLong());
    }


}
