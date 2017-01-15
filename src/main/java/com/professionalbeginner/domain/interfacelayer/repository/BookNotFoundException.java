package com.professionalbeginner.domain.interfacelayer.repository;

import com.professionalbeginner.domain.core.book.BookId;

/**
 * @author Kempenich Florian
 */
public class BookNotFoundException extends Exception {

    private final BookId bookId;

    public BookNotFoundException(BookId bookId) {
        super("Book could not be found: " + bookId);
        this.bookId = bookId;
    }

    public BookId getSearchedId() {
        return bookId;
    }
}
