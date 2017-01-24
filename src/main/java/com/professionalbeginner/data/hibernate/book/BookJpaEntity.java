package com.professionalbeginner.data.hibernate.book;

import com.google.common.base.MoreObjects;

import javax.persistence.*;
import java.util.List;

/**
 * @author Kempenich Florian
 */
@Entity
@Table(name = "book")
public class BookJpaEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String author;
    private int numPages;
    private double price;

    @OneToMany(
            mappedBy = "book",
            fetch = FetchType.LAZY,
            cascade = javax.persistence.CascadeType.ALL,
            orphanRemoval = true)
    private List<ReviewJpaEntity> reviews;

    protected BookJpaEntity() {}

    public BookJpaEntity(String title, String author, int numPages, double price, List<ReviewJpaEntity> reviews) {
        this.title = title;
        this.author = author;
        this.numPages = numPages;
        this.price = price;
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("title", title)
                .add("author", author)
                .add("numPages", numPages)
                .add("price", price)
                .toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getNumPages() {
        return numPages;
    }

    public void setNumPages(int numPages) {
        this.numPages = numPages;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<ReviewJpaEntity> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewJpaEntity> reviews) {
        this.reviews = reviews;
    }
}

