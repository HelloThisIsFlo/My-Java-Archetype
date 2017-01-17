package com.professionalbeginner.presentation.model;

import com.google.common.base.MoreObjects;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author Kempenich Florian
 */
public class ReviewDTO {

    @Min(1)
    private Long bookId;
    @NotEmpty
    private String username;

    @Min(0)
    @Max(100)
    private int rating;

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("bookId", bookId)
                .add("username", username)
                .add("rating", rating)
                .toString();
    }
}
