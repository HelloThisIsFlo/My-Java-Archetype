package com.professionalbeginner.domain.core.book;

import com.google.common.base.MoreObjects;
import com.professionalbeginner._other.ddd.ValueObject;
import com.professionalbeginner.domain.core.review.Rating;
import com.professionalbeginner.domain.core.review.User;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Florian Kempenich
 */
public class ReviewVO implements ValueObject<ReviewVO> {

    public static final ReviewVO NULL = new ReviewVO(
            BookId.NOT_ASSIGNED,
            new User("-1"),
            new Rating(0)
    );

    private BookId bookId;
    private Rating rating;
    private User reviewer;

    public ReviewVO(BookId bookId, User reviewer, Rating rating) {
        this.bookId = checkNotNull(bookId);
        updateRating(rating);
        this.reviewer = checkNotNull(reviewer);
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
        return Objects.hash(bookId, rating, reviewer);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewVO review = (ReviewVO) o;
        return sameValueAs(review);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("bookId", bookId.toString())
                .add("rating", rating.value())
                .add("reviewer", reviewer.username())
                .toString();
    }

    @Override
    public boolean sameValueAs(ReviewVO other) {
        return Objects.equals(bookId, other.bookId) &&
                Objects.equals(rating, other.rating) &&
                Objects.equals(reviewer, other.reviewer);
    }

}
