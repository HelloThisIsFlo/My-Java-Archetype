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

/**
 * @author Kempenich Florian
 */
@Component
@IntegrationTests
public class TestUtils {

    public Book makeBook(BookId id, String title, String author, int numPage, double price) {
        Book book = new Book(
                new Characteristics(title, author, numPage),
                new Price(price)
        );
        book.setId(id);
        return book;
    }

    public Book makeDefaultBook(BookId id) {
        return makeBook(
                id,
                "Default title",
                "Default author",
                456,
                678.89
        );
    }

    public Review makeReview(ReviewId reviewId, BookId bookId, int rating, String reviewerName) {
        return new Review(reviewId, bookId, new User(reviewerName), new Rating(rating));
    }

}
