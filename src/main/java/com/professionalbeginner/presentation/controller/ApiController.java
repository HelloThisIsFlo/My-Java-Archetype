package com.professionalbeginner.presentation.controller;

import com.professionalbeginner.domain.applicationlayer.BookService;
import com.professionalbeginner.domain.core.book.*;
import com.professionalbeginner.domain.core.user.UserId;
import com.professionalbeginner.domain.interfacelayer.repository.BookNotFoundException;
import com.professionalbeginner.presentation.Assembler;
import com.professionalbeginner.presentation.model.BookDTO;
import com.professionalbeginner.presentation.model.BookDetailsDTO;
import com.professionalbeginner.presentation.model.ReviewDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Kempenich Florian
 */
@RestController
public class ApiController {

    private static final Logger LOG = LoggerFactory.getLogger(ApiController.class);

    private final BookService bookService;
    private final Assembler bookAssembler;

    @Autowired
    public ApiController(BookService bookService, Assembler bookAssembler) {
        this.bookService = bookService;
        this.bookAssembler = bookAssembler;
    }


    @RequestMapping("/books")
    @ResponseStatus(HttpStatus.OK)
    public List<BookDTO> retrieveAllBooks() {
        List<Book> allBooks = bookService.getAllBooks();

        return allBooks.stream()
                .map(bookAssembler::toBookDTO)
                .collect(Collectors.toList());
    }

    @RequestMapping("/books/detail")
    @ResponseStatus(HttpStatus.OK)
    public BookDetailsDTO retrieveBookDetails(@RequestParam("bookId") Long id) throws BookNotFoundException {
        BookId bookId = new BookId(id);

        Book book = bookService.getDetails(bookId);
        double avgRating = bookService.getAverageRating(bookId);

        return bookAssembler.toBookDetailsDTO(book, avgRating);
    }

    @RequestMapping(value = "/books/new", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public BookDTO createNewBook(@RequestBody @Valid BookDTO bookDTO) {
        Characteristics characteristics = new Characteristics(
                bookDTO.getTitle(),
                bookDTO.getAuthor(),
                bookDTO.getNumPages()
        );
        Price price = new Price(bookDTO.getPrice());

        BookId id = bookService.createNewBookEntry(characteristics, price);

        bookDTO.setBookId(id.idLong());
        return bookDTO;
    }

    @RequestMapping(value = "/books/newReview", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public ReviewDTO addNewReview(@RequestBody @Valid ReviewDTO reviewDTO) {
        BookId bookId = new BookId(reviewDTO.getBookId());
        Rating rating = new Rating(reviewDTO.getRating());
        UserId reviewer = new UserId(reviewDTO.getUsername());

        bookService.addNewReview(bookId, rating, reviewer);

        return reviewDTO;
    }



}
