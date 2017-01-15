package com.professionalbeginner.data.hibernate.model;

import com.google.common.base.MoreObjects;

import javax.persistence.*;

/**
 * @author Kempenich Florian
 */
@Entity
public class ReviewJpaEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "book_id")
    private BookJpaEntity book;

    private int rating;
    private String reviewer;

    protected ReviewJpaEntity() {}

    public ReviewJpaEntity(Long bookId, int rating, String reviewerUsername) {
        setBookId(bookId);
        this.rating = rating;
        this.reviewer = reviewerUsername;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("bookId", book)
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
        return book.getId();
    }

    public void setBookId(Long bookId) {
        BookJpaEntity bookJpaEntity = new BookJpaEntity();
        bookJpaEntity.setId(bookId);
        this.book = bookJpaEntity;
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
