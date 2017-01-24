package com.professionalbeginner.data.hibernate.book;

import com.professionalbeginner.domain.core.book.*;
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
public class BookJpaMapper {

    private static final Logger LOG = LoggerFactory.getLogger(BookJpaMapper.class);

    public BookJpaEntity map(Book book) {
        BookJpaEntity jpaBook = new BookJpaEntity(
                book.characteristics().title(),
                book.characteristics().author(),
                book.characteristics().numPages(),
                book.price().amount(),
                null
        );
        List<ReviewJpaEntity> reviews = mapAllToORM(book.getReviews(), jpaBook);
        jpaBook.setReviews(reviews);

        if (!book.id().sameValueAs(BookId.NOT_ASSIGNED)) {
            jpaBook.setId(book.id().idLong());
        }

        return jpaBook;
    }

    private List<ReviewJpaEntity> mapAllToORM(List<Review> reviews, BookJpaEntity bookJpaEntity) {
        return reviews.stream()
                .map(review -> map(review, bookJpaEntity))
                .collect(Collectors.toList());
    }

    public ReviewJpaEntity map(Review review, BookJpaEntity book) {
        ReviewJpaEntity jpaEntity = new ReviewJpaEntity(
                book,
                review.getRating().value(),
                review.getReviewer().username()
        );
        return jpaEntity;
    }

    public BookId mapId(BookJpaEntity jpaBook) {
        return new BookId(jpaBook.getId());
    }

    public Review map(ReviewJpaEntity reviewJpaEntity) {
        BookId bookId = new BookId(reviewJpaEntity.getBook().getId());
        UserId reviewer = new UserId(reviewJpaEntity.getReviewer());
        Rating rating = new Rating(reviewJpaEntity.getRating());

        return new Review(bookId, reviewer, rating);
    }

    public List<Book> mapAllToDomain(List<BookJpaEntity> jpaEntities, boolean withReviews) {
        return jpaEntities.stream()
                .map(bookJpa -> map(bookJpa, withReviews))
                .collect(Collectors.toList());
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

    private void addAllReviews(Book book, List<ReviewJpaEntity> reviewJpaEntities) {
        reviewJpaEntities.stream()
                .map(this::map)
                .forEach(book::addReview);
    }
}

