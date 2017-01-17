package com.professionalbeginner.presentation;

import com.professionalbeginner.domain.core.book.Book;
import com.professionalbeginner.domain.core.review.Review;
import com.professionalbeginner.presentation.model.BookDTO;
import com.professionalbeginner.presentation.model.ReviewDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Kempenich Florian
 */
@Component
public class Assembler {

    public BookDTO toDTO(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setBookId(book.id().idLong());
        bookDTO.setTitle(book.characteristics().title());
        bookDTO.setAuthor(book.characteristics().author());
        bookDTO.setNumPages(book.characteristics().numPages());
        bookDTO.setPrice(book.price().amount());

        List<ReviewDTO> reviews = book.getReviews().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        bookDTO.setReviews(reviews);

        return bookDTO;
    }

    public BookDTO toDTO(Book book, double avgRating) {
        BookDTO bookDTO = toDTO(book);
        bookDTO.setAvgRating(avgRating);
        return bookDTO;
    }

    public ReviewDTO toDTO(Review review) {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setBookId(review.getBookId().idLong());
        reviewDTO.setRating(review.getRating().value());
        reviewDTO.setUsername(review.getReviewer().username());
        return reviewDTO;
    }
}
