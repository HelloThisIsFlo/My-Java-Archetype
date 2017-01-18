package com.professionalbeginner.domain.core.book;

import com.google.common.base.MoreObjects;
import com.professionalbeginner.domain.core.review.ReviewId;
import com.professionalbeginner._other.ddd.Entity;
import com.professionalbeginner.domain.core.review.IllegalReviewException;
import com.professionalbeginner.domain.core.review.Review;
import com.professionalbeginner.domain.core.review.User;

import java.util.*;
import java.util.stream.Collectors;

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
    private Map<ReviewId, ReviewVO> reviews;

    public Book(Characteristics characteristics, Price price) {
        this.characteristics = checkNotNull(characteristics);
        this.price = checkNotNull(price);
        this.reviews = new HashMap<>();
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
        return reviews.values().stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    private Review map(ReviewVO vo) {
        return new Review(
                findId(vo),
                vo.getBookId(),
                vo.getReviewer(),
                vo.getRating()
        );
    }

    private ReviewId findId(ReviewVO candidate) {
        for (Map.Entry<ReviewId, ReviewVO> reviewIdReviewVOEntry : reviews.entrySet()) {
            if (reviewIdReviewVOEntry.getValue().equals(candidate)) {
                return reviewIdReviewVOEntry.getKey();
            }
        }
        return null;
    }

    public void addReview(Review review) {
        checkIfValid(review);
        ReviewVO vo = new ReviewVO(review.getBookId(), review.getReviewer(), review.getRating());
        reviews.put(review.getId(), vo);
    }

    public void updatePrice(Price newPrice) {
        this.price = checkNotNull(newPrice);
    }

    private void checkIfValid(Review review) {
        checkIfReviewHasPersistedId(review);
        checkIfCorrectBookId(review);
        checkIfNoExistingReview(review);
    }

    private void checkIfReviewHasPersistedId(Review review) {
        if (review.getId().sameValueAs(ReviewId.NOT_ASSIGNED)) {
            throw new IllegalReviewException("Cannot accept a review with un-assigned id, persist it first", review);
        }
    }

    private void checkIfCorrectBookId(Review review) {
        if (!review.getBookId().sameValueAs(id)) {
            throw new IllegalReviewException("Wrong Book Id", review);
        }
    }

    private void checkIfNoExistingReview(Review review) {
        User user = review.getReviewer();
        reviews.values().stream()
                .filter(r -> r.getReviewer().sameValueAs(user))
                .findAny()
                .map(this::map)
                .ifPresent(existingReview -> {
                    throw new IllegalReviewException("Review existing for this book for this user", existingReview);
                });
    }
}
