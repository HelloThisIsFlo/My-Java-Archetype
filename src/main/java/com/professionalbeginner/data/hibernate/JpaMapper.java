package com.professionalbeginner.data.hibernate;

import com.professionalbeginner.data.hibernate.model.BookJpaEntity;
import com.professionalbeginner.data.hibernate.model.ReviewJpaEntity;
import com.professionalbeginner.domain.core.book.*;
import com.professionalbeginner.domain.core.book.Rating;
import com.professionalbeginner.domain.core.user.UserId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Kempenich Florian
 */
@Component
public class JpaMapper {

    private static final Logger LOG = LoggerFactory.getLogger(JpaMapper.class);

    public BookJpaEntity map(Book book) {
        BookJpaEntity jpaBook = new BookJpaEntity(
                book.characteristics().title(),
                book.characteristics().author(),
                book.characteristics().numPages(),
                book.price().amount(),
                mapAllToORM(book.getReviews())
        );

        if (!book.id().sameValueAs(BookId.NOT_ASSIGNED)) {
            jpaBook.setId(book.id().idLong());
        }

        return jpaBook;
    }

    public Book map(BookJpaEntity bookJpaEntity, boolean withReviews) {
        Characteristics characteristics = new Characteristics(
                bookJpaEntity.getTitle(),
                bookJpaEntity.getAuthor(),
                bookJpaEntity.getNumPages()
        );
        Price price = new Price(bookJpaEntity.getPrice());
        BookId bookId = new BookId(bookJpaEntity.getId());

        Book book = new Book(characteristics, price);
        book.setId(bookId);
        if (withReviews) {
            /*
            Reviews are lazily fetched.
            A call to the `Review` table is only made if `getReviews()` is called on the `bookJpaEntity`.
            Skipping this part, skips the call to the database.
            */
            addAllReviews(book, bookJpaEntity.getReviews());
        }

        return book;
    }

    public BookId mapId(BookJpaEntity jpaBook) {
        return new BookId(jpaBook.getId());
    }

    private void addAllReviews(Book book, List<ReviewJpaEntity> reviewJpaEntities) {
        reviewJpaEntities.stream()
                .map(this::map)
                .forEach(book::addReview);
    }

    public ReviewJpaEntity map(Review review) {
        ReviewJpaEntity jpaEntity = new ReviewJpaEntity(
                review.getBookId().idLong(),
                review.getRating().value(),
                review.getReviewer().username()
        );
        return jpaEntity;
    }

    public Review map(ReviewJpaEntity reviewJpaEntity) {
        BookId bookId = new BookId(reviewJpaEntity.getBookId());
        UserId reviewer = new UserId(reviewJpaEntity.getReviewer());
        Rating rating = new Rating(reviewJpaEntity.getRating());

        return new Review(bookId, reviewer, rating);
    }

    public List<ReviewJpaEntity> mapAllToORM(List<Review> reviews) {
        return reviews.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    public List<Book> mapAllToDomain(List<BookJpaEntity> jpaEntities, boolean withReviews) {
        return jpaEntities.stream()
                .map(bookJpa -> map(bookJpa, withReviews))
                .collect(Collectors.toList());
    }
}

