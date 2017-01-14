package com.professionalbeginner.domain.core.review;

import com.google.common.base.MoreObjects;
import com.professionalbeginner.domain.core.book.BookId;
import com.professionalbeginner.domain.ddd.Entity;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Represents a book review.
 *
 * @author Kempenich Florian
 */
public class Review implements Entity<Review> {

    public static final Review NULL = new Review(
            new ReviewId("-1"),
            new BookId("-1"),
            new User("-1"), new Rating(0)
    );

    private ReviewId id;
    private BookId bookId;
    private Rating rating;
    private User reviewer;

    public Review(ReviewId id, BookId bookId, User reviewer, Rating rating) {
        this.id = checkNotNull(id);
        this.bookId = checkNotNull(bookId);
        updateRating(rating);
        this.reviewer = checkNotNull(reviewer);
    }

    public ReviewId getId() {
        return id;
    }

    public BookId getBookId() {
        return bookId;
    }

    public Rating getRating() {
        return rating;
    }

    public void updateRating(Rating rating) {
        this.rating = checkNotNull(rating);
    }

    public User getReviewer() {
        return reviewer;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return sameIdentityAs(review);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id.idString())
                .add("bookId", bookId.idString())
                .add("rating", rating.value())
                .add("reviewer", reviewer.username())
                .toString();
    }

    @Override
    public boolean sameIdentityAs(Review other) {
        return other.id.sameValueAs(id);
    }
}
