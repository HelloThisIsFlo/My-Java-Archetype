package com.professionalbeginner.domain.interfacelayer.repository;

import com.professionalbeginner.domain.core.book.Book;
import com.professionalbeginner.domain.core.book.BookId;

import java.util.List;

/**
 * @author Kempenich Florian
 */
public interface BookRepository {

    BookId save(Book book);

    Book findById(BookId id, boolean withReviews);

    List<Book> findAll(boolean withReviews);
}
