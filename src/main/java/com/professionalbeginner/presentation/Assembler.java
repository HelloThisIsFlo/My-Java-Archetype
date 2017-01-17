package com.professionalbeginner.presentation;

import com.professionalbeginner.domain.core.book.Book;
import com.professionalbeginner.presentation.model.BookDTO;
import org.springframework.stereotype.Component;

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
        return bookDTO;
    }
}
