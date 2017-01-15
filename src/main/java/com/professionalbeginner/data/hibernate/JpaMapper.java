package com.professionalbeginner.data.hibernate;

import com.professionalbeginner.data.hibernate.model.BookJpaEntity;
import com.professionalbeginner.data.hibernate.model.ReviewJpaEntity;
import com.professionalbeginner.domain.core.book.Book;
import com.professionalbeginner.domain.core.book.BookId;
import com.professionalbeginner.domain.core.book.Characteristics;
import com.professionalbeginner.domain.core.book.Price;
import com.professionalbeginner.domain.core.review.Rating;
import com.professionalbeginner.domain.core.review.Review;
import com.professionalbeginner.domain.core.review.ReviewId;
import com.professionalbeginner.domain.core.review.User;
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

    public Book map(BookJpaEntity bookJpaEntity) {
        Characteristics characteristics = new Characteristics(
                bookJpaEntity.getTitle(),
                bookJpaEntity.getAuthor(),
                bookJpaEntity.getNumPages()
        );
        Price price = new Price(bookJpaEntity.getPrice());
        BookId bookId = new BookId(bookJpaEntity.getId());

        Book book = new Book(characteristics, price);
        book.setId(bookId);
        addAllReviews(book, bookJpaEntity.getReviews());

        return book;
    }

    public BookId mapId(BookJpaEntity jpaBook) {
        return new BookId(jpaBook.getId());
    }

    public ReviewId mapId(ReviewJpaEntity reviewJpa) {
        return new ReviewId(reviewJpa.getId());
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
        ReviewId id = new ReviewId(reviewJpaEntity.getId());
        BookId bookId = new BookId(reviewJpaEntity.getBookId());
        User reviewer = new User(reviewJpaEntity.getReviewer());
        Rating rating = new Rating(reviewJpaEntity.getRating());

        return new Review(id, bookId, reviewer, rating);
    }

    public List<ReviewJpaEntity> mapAllToORM(List<Review> reviews) {
        return reviews.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    public List<Book> mapAllToDomain(List<BookJpaEntity> jpaEntities) {
        return jpaEntities.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }
}

