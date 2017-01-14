package com.professionalbeginner.data.hibernate.model;

import com.google.common.base.MoreObjects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Kempenich Florian
 */
@Entity
public class ReviewJpaEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Long bookId;
    private int rating;
    private String reviewer;

    protected ReviewJpaEntity() {}

    public ReviewJpaEntity(Long bookId, int rating, String reviewerUsername) {
        this.bookId = bookId;
        this.rating = rating;
        this.reviewer = reviewerUsername;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("bookId", bookId)
                .add("rating", rating)
                .add("reviewer", reviewer)
                .toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }
}
