package com.professionalbeginner.data.inmemory;

import com.professionalbeginner.domain.core.book.Book;
import com.professionalbeginner.domain.core.book.BookId;
import com.professionalbeginner.domain.interfacelayer.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kempenich Florian
 */
public class InMemoryBookRepository implements BookRepository {

    private List<Book> books;

    public InMemoryBookRepository() {
        books = new ArrayList<>();
    }

    @Override
    public BookId save(Book book) {
        long id = books.size() + 1;
        books.add(book);
        return new BookId(id);
    }

    @Override
    public Book findById(BookId id, boolean withReviews) {
        Book book =  books.get(((int) id.idLong()) - 1);
        if(!withReviews) {
            book = copyWithoutReviews(book);
        }
        return book;
    }

    @Override
    public List<Book> findAll(boolean withReviews) {
        throw new IllegalStateException("Not implemented");
    }

    private Book copyWithoutReviews(Book book) {
        return new Book(book.characteristics(), book.price());
    }
}
