package com.professionalbeginner.domain.applicationlayer;

import com.professionalbeginner.domain.core.book.Book;
import com.professionalbeginner.domain.core.book.BookId;
import com.professionalbeginner.domain.core.book.Characteristics;
import com.professionalbeginner.domain.core.book.Price;
import com.professionalbeginner.domain.core.book.IllegalReviewException;
import com.professionalbeginner.domain.core.book.Rating;
import com.professionalbeginner.domain.core.user.UserId;
import com.professionalbeginner.domain.interfacelayer.repository.BookNotFoundException;

import java.util.List;

/**
 * @author Kempenich Florian
 */
public interface BookService {

    /**
     * Creates a new book and return the Id.
     * @return Id of the created book
     */
    BookId createNewBookEntry(Characteristics characteristics, Price price);

    /**
     * Retrieves the details of a book, including all the reviews
     * @return Book with reviews
     */
    Book getDetails(BookId id) throws BookNotFoundException;

    /**
     * Retrieves the list of all books.
     * Without Reviews
     * @return All books, without the reviews
     */
    List<Book> getAllBooks();

    /**
     * Add a new Review to an existing book.
     *
     * @param bookId
     * @param rating
     * @param reviewer
     * @throws IllegalReviewException
     *         In case there is an existing review for this user on this book.
     *         Or if book is not found
     */
    void addNewReview(BookId bookId, Rating rating, UserId reviewer) throws IllegalReviewException;

    /**
     * Computes the average rating for a book
     */
    double getAverageRating(BookId id) throws BookNotFoundException;
}
