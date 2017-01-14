package com.professionalbeginner;

import com.professionalbeginner.domain.core.book.Book;
import com.professionalbeginner.domain.core.book.BookId;
import com.professionalbeginner.domain.core.book.Characteristics;
import com.professionalbeginner.domain.core.book.Price;
import com.professionalbeginner.domain.core.review.Rating;
import com.professionalbeginner.domain.core.review.Review;
import com.professionalbeginner.domain.core.review.ReviewId;
import com.professionalbeginner.domain.core.review.User;
import com.professionalbeginner.spring.IntegrationTests;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @author Kempenich Florian
 */
@Component
@IntegrationTests
public class TestUtils {

    public Book makeBook(String id, String title, String author, int numPage, double price) {
        Book book = new Book(
                new Characteristics(title, author, numPage),
                new Price(price)
        );
        book.setId(new BookId(id));
        return book;
    }

    public Book makeDefaultBook(String id) {
        return makeBook(
                id,
                "Default title",
                "Default author",
                456,
                678.89
        );
    }

    public Review makeRandomReview(BookId bookId) {
        Random random = new Random();
        User user = new User(Integer.toString(random.nextInt(5000)));
        return makeRandomReview(random, bookId, user);
    }

    public Review makeRandomReview(BookId bookId, User user) {
        return makeRandomReview(new Random(), bookId, user);
    }

    private Review makeRandomReview(Random random, BookId bookId, User user) {
        Rating rating = new Rating(random.nextInt(100));
        ReviewId reviewId = new ReviewId(Integer.toString(random.nextInt()));
        return new Review(reviewId, bookId, user, rating);
    }
}
