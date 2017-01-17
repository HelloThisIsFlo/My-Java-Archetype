package com.professionalbeginner._other.temp;

import com.professionalbeginner.domain.core.book.Book;
import com.professionalbeginner.domain.core.book.BookId;
import com.professionalbeginner.domain.core.book.Characteristics;
import com.professionalbeginner.domain.core.book.Price;
import com.professionalbeginner.domain.core.review.Rating;
import com.professionalbeginner.domain.core.review.Review;
import com.professionalbeginner.domain.core.review.ReviewId;
import com.professionalbeginner.domain.core.review.User;
import com.professionalbeginner.domain.interfacelayer.repository.BookRepository;
import com.professionalbeginner.domain.interfacelayer.repository.ReviewRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author Kempenich Florian
 */
@Component
public class InitDbWithFakeBooks implements CommandLineRunner{

    private static final Logger LOG = LoggerFactory.getLogger(InitDbWithFakeBooks.class);
    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;

    @Autowired
    public InitDbWithFakeBooks(BookRepository bookRepository, ReviewRepository reviewRepository) {
        this.bookRepository = bookRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        for (int i = 0; i < 100; i++) {
            LOG.info("Yep it's running");
        }

        Book book1 = makeBook("This title", "Author", 334, 123.3);
        Book book2 = makeBook("second title", "second Author", 983, 723.3);
        Book book3 = makeBook("coucou", "Patrick", 33, 1223.3);
        Book book4 = makeBook("Hey :D", "Mark zucker", 4, 1.3);
        bookRepository.save(book1);
        bookRepository.save(book2);
        BookId id3 = bookRepository.save(book3);
        bookRepository.save(book4);

        book3.setId(id3);
        Review review1 = makeReview(id3, 33, "Frank");
        Review review2 = makeReview(id3, 12, "Patrick");
        Review review3 = makeReview(id3, 87, "Mark");
        List<Review> reviewsBook3 = Arrays.asList(review1, review2, review3);

        reviewsBook3.stream()
                .map(review -> {
                    ReviewId id = reviewRepository.save(review);
                    review.setId(id);
                    return review;
                })
                .forEach(book3::addReview);


    }

    private Book makeBook(String title, String author, int numPage, double price) {
        Book book = new Book(
                new Characteristics(title, author, numPage),
                new Price(price)
        );
        book.setId(BookId.NOT_ASSIGNED);
        return book;
    }

    private Review makeReview(BookId bookId, int rating, String reviewerName) {
        return new Review(ReviewId.NOT_ASSIGNED, bookId, new User(reviewerName), new Rating(rating));
    }
}
