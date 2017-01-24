package com.professionalbeginner.data.hibernate.book;

import com.google.common.base.MoreObjects;

import javax.persistence.*;

/**
 * @author Kempenich Florian
 */
@Entity
@Table(name = "review")
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

    public ReviewJpaEntity(BookJpaEntity book, int rating, String reviewerUsername) {
        this.book = book;
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

    public BookJpaEntity getBook() {
        return book;
    }

    public void setBook(BookJpaEntity book) {
        this.book = book;
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
