package com.professionalbeginner.domain.core.review;

import com.google.common.base.MoreObjects;
import com.professionalbeginner.domain.ddd.ValueObject;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Identifies a review
 *
 * @author Kempenich Florian
 */
public class ReviewId implements ValueObject<ReviewId> {

    private String id;

    public ReviewId(String id) {
        checkNotNull(id);
        checkArgument(!id.isEmpty(), "Id cannot be empty");
        this.id = id;
    }

    public String idString() {
        return id;
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
}
