package com.professionalbeginner.data.inmemory;

import com.professionalbeginner._other.spring.Dev;
import com.professionalbeginner.domain.core.book.Book;
import com.professionalbeginner.domain.core.book.BookId;
import com.professionalbeginner.domain.interfacelayer.repository.BookNotFoundException;
import com.professionalbeginner.domain.interfacelayer.repository.BookRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Kempenich Florian
 */
@Dev
@Repository
public class InMemoryBookRepository implements BookRepository {

    private List<Book> books;

    public InMemoryBookRepository() {
        books = new ArrayList<>();
    }

    @Override
    public BookId save(Book book) {
        if (hasBook(book.id())) {
            return updateExisting(book);
        } else {
            return saveNew(book);
        }
    }

    private boolean hasBook(BookId id) {
        return id.idLong() >= 1 && id.idLong() <= books.size();
    }

    private BookId updateExisting(Book book) {
        BookId id = book.id();
        int index = getIndex(id);

        books.remove(index);
        books.add(index, book);

        return id;
    }

    private int getIndex(BookId id) {
        return (int) (id.idLong() - 1);
    }

    private BookId saveNew(Book book) {
        long id = books.size() + 1;
        BookId bookId = new BookId(id);
        book.setId(bookId);
        books.add(book);
        return bookId;
    }

    @Override
    public Book findById(BookId id, boolean withReviews) throws BookNotFoundException {
        try {
            Book book = books.get(getIndex(id));
            if(!withReviews) {
                book = copyWithoutReviews(book);
            }
            return book;
        } catch (Exception e) {
            throw new BookNotFoundException(id);
        }
    }

    @Override
    public List<Book> findAll(boolean withReviews) {
        List<Book> result = new ArrayList<>(books);
        if (withReviews) {
            return result;
        } else {
            return result.stream()
                    .map(this::copyWithoutReviews)
                    .collect(Collectors.toList());
        }
    }

    private Book copyWithoutReviews(Book book) {
        Book copy = new Book(book.characteristics(), book.price());
        copy.setId(book.id());
        return copy;
    }
}
