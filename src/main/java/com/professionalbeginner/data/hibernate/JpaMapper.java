package com.professionalbeginner.data.hibernate;

import com.professionalbeginner.data.hibernate.model.BookJpaEntity;
import com.professionalbeginner.domain.core.book.Book;
import com.professionalbeginner.domain.core.book.BookId;
import com.professionalbeginner.domain.core.book.Characteristics;
import com.professionalbeginner.domain.core.book.Price;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Kempenich Florian
 */
@Component
public class JpaMapper {

    private static final Logger LOG = LoggerFactory.getLogger(JpaMapper.class);

    public BookJpaEntity map(Book book) {
        BookJpaEntity jpaBook = new BookJpaEntity(
                book.characteristics().title(),
                book.characteristics().author(),
                book.characteristics().numPages(),
                book.price().amount()
        );

        if (!book.id().sameValueAs(BookId.NOT_ASSIGNED)) {
            jpaBook.setId(parseId(book));
        }

        return jpaBook;
    }

    public Book map(BookJpaEntity bookJpaEntity) {
        Characteristics characteristics = new Characteristics(
                bookJpaEntity.getTitle(),
                bookJpaEntity.getAuthor(),
                bookJpaEntity.getNumPages()
        );
        Price price = new Price(bookJpaEntity.getPrice());

        Book book = new Book(characteristics, price);
        book.setId(getBookId(bookJpaEntity));

        return book;
    }

    private BookId getBookId(BookJpaEntity bookJpaEntity) {
        return new BookId(bookJpaEntity.getId().toString());
    }

    private Long parseId(Book book) {
        return Long.parseLong(book.id().idString());
    }
}

