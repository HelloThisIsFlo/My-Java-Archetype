package com.professionalbeginner.domain.core.book;

import com.google.common.base.MoreObjects;
import com.professionalbeginner.domain.ddd.Entity;
import com.professionalbeginner.domain.core.review.IllegalReviewException;
import com.professionalbeginner.domain.core.review.Review;
import com.professionalbeginner.domain.core.review.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Kempenich Florian
 */
public class Book implements Entity<Book> {

    public final static Book NULL = new Book(
            new Characteristics("-1", "-1", 1),
            new Price(0)
    );

    /*
     * Note to self:
     * Here the id is NOT_ASSIGNED until the entity is persisted. Then the repository will assign an id.
     * The service creating the entity simply will read the id after its been persisted.
     */
    private BookId id = BookId.NOT_ASSIGNED;

    private Characteristics characteristics;
    private Price price;
    private List<Review> reviews;

    public Book(Characteristics characteristics, Price price) {
        this.characteristics = checkNotNull(characteristics);
        this.price = checkNotNull(price);
        this.reviews = new ArrayList<>();
    }

    public void setId(BookId id) {
        this.id = checkNotNull(id);
    }

    public BookId id() {
        return id;
    }

    public Characteristics characteristics() {
        return characteristics;
    }

    public Price price() {
        return price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return sameIdentityAs(book);
    }

    @Override
    public boolean sameIdentityAs(Book other) {
        return Objects.equals(id, other.id);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("characteristics", characteristics)
                .add("price", price)
                .toString();
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void addReview(Review review) {
        checkIfValid(review);
        reviews.add(review);
    }

    public void updatePrice(Price newPrice) {
        this.price = checkNotNull(newPrice);
    }

    private void checkIfValid(Review review) {
        checkIfCorrectBookId(review);
        checkIfNoExistingReview(review);
    }

    private void checkIfCorrectBookId(Review review) {
        if (!review.getBookId().sameValueAs(id)) {
            throw new IllegalReviewException("Wrong Book Id", review);
        }
    }

    private void checkIfNoExistingReview(Review review) {
        User user = review.getReviewer();
        reviews.stream()
                .filter(r -> r.getReviewer().sameValueAs(user))
                .findAny()
                .ifPresent(existingReview -> {
                    throw new IllegalReviewException("Review existing for this book for this user", existingReview);
                });
    }
}
