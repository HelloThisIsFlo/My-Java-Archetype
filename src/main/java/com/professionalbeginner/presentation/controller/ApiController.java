package com.professionalbeginner.presentation.controller;

import com.professionalbeginner.domain.applicationlayer.BookService;
import com.professionalbeginner.domain.core.book.Book;
import com.professionalbeginner.domain.core.book.BookId;
import com.professionalbeginner.domain.core.review.Rating;
import com.professionalbeginner.domain.core.review.User;
import com.professionalbeginner.domain.interfacelayer.repository.BookNotFoundException;
import com.professionalbeginner.presentation.model.BookDTO;
import com.professionalbeginner.presentation.Assembler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Kempenich Florian
 */
@RestController
public class ApiController {

    private static final Logger LOG = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    BookService bookService;
    @Autowired
    Assembler bookAssembler;

    @RequestMapping("/books")
    @ResponseStatus(HttpStatus.OK)
    public List<BookDTO> getAllBooks() {
        List<Book> allBooks = bookService.getAllBooks();

        return allBooks.stream()
                .map(bookAssembler::toDTO)
                .collect(Collectors.toList());
    }

    @RequestMapping("/books/detail")
    @ResponseStatus(HttpStatus.OK)
    public BookDTO getBookDetails(@RequestParam("bookId") Long id) throws BookNotFoundException {
        BookId bookId = new BookId(id);

        Book book = bookService.getDetails(bookId);
        double avgRating = bookService.getAverageRating(bookId);

        return bookAssembler.toDTO(book, avgRating);
    }

    @RequestMapping("/test")
    @ResponseStatus(HttpStatus.OK)
    public void test(@RequestParam("bookId") Long bookId) {
        // TODO: 1/17/2017 remove 
        BookId id = new BookId(bookId);
        Rating rating = new Rating(12);
        User reviewer = new User("patrick");
        bookService.addNewReview(id, rating, reviewer);
    }

    public void createNewBook() {
        // TODO: 1/17/2017 todo
    }

    public void addNewReview() {
        // TODO: 1/17/2017
    }



}
