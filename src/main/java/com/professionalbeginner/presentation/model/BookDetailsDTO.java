package com.professionalbeginner.presentation.model;

import java.util.List;

/**
 * @author Kempenich Florian
 */
public class BookDetailsDTO {

    private BookDTO book;

    private List<ReviewDTO> reviews;

    private double averageRating;

    public BookDTO getBook() {
        return book;
    }

    public void setBook(BookDTO book) {
        this.book = book;
    }

    public List<ReviewDTO> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewDTO> reviews) {
        this.reviews = reviews;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }
}
