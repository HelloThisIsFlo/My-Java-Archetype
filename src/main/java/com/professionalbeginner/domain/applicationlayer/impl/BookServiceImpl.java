package com.professionalbeginner.domain.applicationlayer.impl;

import com.professionalbeginner.domain.applicationlayer.BookService;
import com.professionalbeginner.domain.core.book.*;
import com.professionalbeginner.domain.core.user.UserId;
import com.professionalbeginner.domain.interfacelayer.repository.BookNotFoundException;
import com.professionalbeginner.domain.interfacelayer.repository.BookRepository;
import com.professionalbeginner.domain.interfacelayer.statistics.StatisticsContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Kempenich Florian
 */
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final StatisticsContract statistics;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, StatisticsContract statistics) {
        this.bookRepository = bookRepository;
        this.statistics = statistics;
    }

    @Override
    public BookId createNewBookEntry(Characteristics characteristics, Price price) {
        Book book = new Book(characteristics, price);
        return bookRepository.save(book);
    }

    @Override
    public Book getDetails(BookId id) throws BookNotFoundException {
        return bookRepository.findById(id,true);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll(false);
    }

    @Override
    public void addNewReview(BookId bookId, Rating rating, UserId reviewer) throws IllegalReviewException {
        try {
            Book fromRepo = bookRepository.findById(bookId, true);
            Review toAdd = new Review(bookId, reviewer, rating);
            fromRepo.addReview(toAdd);
            bookRepository.save(fromRepo);
        } catch (BookNotFoundException e) {
            throw new IllegalReviewException("Book not found");
        }
    }

    @Override
    public double getAverageRating(BookId id) throws BookNotFoundException {
        Book book = bookRepository.findById(id, true);
        return processAverageRating(book.getReviews());
    }

    private double processAverageRating(List<Review> reviews) {
        Integer[] ratings = reviews.stream()
                .map(Review::getRating)
                .map(Rating::value)
                .toArray(Integer[]::new);
        return statistics.processAverage(ratings);
    }
}
