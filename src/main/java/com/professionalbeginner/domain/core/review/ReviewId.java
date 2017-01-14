package com.professionalbeginner.domain.core.review;

import com.google.common.base.MoreObjects;
import com.professionalbeginner.domain.ddd.ValueObject;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Identifies a review
 *
 * @author Kempenich Florian
 */
public class ReviewId implements ValueObject<ReviewId> {

    public static final ReviewId NOT_ASSIGNED = new ReviewId();

    private Long id;

    private ReviewId() {
        this.id = -1L;
    }

    public ReviewId(Long id) {
        checkArgument(id > 0, "Id cannot be <= 0");
        this.id = id;
    }

    @Override
    public boolean sameValueAs(ReviewId other) {
        return other.id.equals(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewId reviewId = (ReviewId) o;
        return sameValueAs(reviewId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .toString();
    }

    public long idLong() {
        return id;
    }
}
