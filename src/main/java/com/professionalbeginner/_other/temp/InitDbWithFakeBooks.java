package com.professionalbeginner._other.temp;

import com.professionalbeginner.domain.core.book.Book;
import com.professionalbeginner.domain.core.book.BookId;
import com.professionalbeginner.domain.core.book.Characteristics;
import com.professionalbeginner.domain.core.book.Price;
import com.professionalbeginner.domain.interfacelayer.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author Kempenich Florian
 */
@Component
public class InitDbWithFakeBooks implements CommandLineRunner{

    private static final Logger LOG = LoggerFactory.getLogger(InitDbWithFakeBooks.class);
    private final BookRepository bookRepository;

    @Autowired
    public InitDbWithFakeBooks(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        for (int i = 0; i < 100; i++) {
            LOG.info("Yep it's running");
        }

        Book book1 = makeBook("This title", "Author", 334, 123.3);
        Book book2 = makeBook("second title", "second Author", 983, 723.3);
        Book book3 = makeBook("coucou", "Patrick", 33, 1223.3);
        Book book4 = makeBook("Hey :D", "Mark zucker", 4, 1.3);
        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);
        bookRepository.save(book4);

    }

    public Book makeBook(String title, String author, int numPage, double price) {
        Book book = new Book(
                new Characteristics(title, author, numPage),
                new Price(price)
        );
        book.setId(BookId.NOT_ASSIGNED);
        return book;
    }
}
