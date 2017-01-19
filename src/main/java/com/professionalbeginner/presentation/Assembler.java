package com.professionalbeginner.presentation;

import com.professionalbeginner.domain.core.book.Book;
import com.professionalbeginner.domain.core.book.Review;
import com.professionalbeginner.presentation.model.BookDTO;
import com.professionalbeginner.presentation.model.BookDetailsDTO;
import com.professionalbeginner.presentation.model.ReviewDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Kempenich Florian
 */
@Component
public class Assembler {

    public BookDTO toBookDTO(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setBookId(book.id().idLong());
        bookDTO.setTitle(book.characteristics().title());
        bookDTO.setAuthor(book.characteristics().author());
        bookDTO.setNumPages(book.characteristics().numPages());
        bookDTO.setPrice(book.price().amount());
        return bookDTO;
    }

    public BookDetailsDTO toBookDetailsDTO(Book book, double avgRating) {
        BookDTO bookDTO = toBookDTO(book);

        BookDetailsDTO bookDetailsDTO = new BookDetailsDTO();

        bookDetailsDTO.setBook(bookDTO);
        List<ReviewDTO> reviews = book.getReviews().stream()
                .map(this::toReviewDTO)
                .collect(Collectors.toList());
        bookDetailsDTO.setReviews(reviews);
        bookDetailsDTO.setAverageRating(avgRating);

        return bookDetailsDTO;
    }

    public ReviewDTO toReviewDTO(Review review) {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setBookId(review.getBookId().idLong());
        reviewDTO.setRating(review.getRating().value());
        reviewDTO.setUsername(review.getReviewer().username());
        return reviewDTO;
    }
}
