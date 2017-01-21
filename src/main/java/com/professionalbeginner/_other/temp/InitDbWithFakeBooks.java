package com.professionalbeginner._other.temp;

import com.professionalbeginner._other.spring.Dev;
import com.professionalbeginner._other.spring.Prod;
import com.professionalbeginner.domain.core.book.*;
import com.professionalbeginner.domain.core.user.User;
import com.professionalbeginner.domain.core.user.UserId;
import com.professionalbeginner.domain.core.user.UserInfo;
import com.professionalbeginner.domain.interfacelayer.repository.BookRepository;
import com.professionalbeginner.domain.interfacelayer.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * @author Kempenich Florian
 */
@Prod
@Dev
@Component
public class InitDbWithFakeBooks implements CommandLineRunner{

    private static final Logger LOG = LoggerFactory.getLogger(InitDbWithFakeBooks.class);
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Autowired
    public InitDbWithFakeBooks(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        for (int i = 0; i < 100; i++) {
            LOG.info("Init Database with fake books       (displayed 100 times for visibility)");
        }

        User frank = makeUser("Frank", "frank", "schmidt", 39);
        User patrick = makeUser("Patrick33", "patrick", "lastname", 19);
        User mark = makeUser("Mark", "Mark", "Grey", 77);
        userRepository.save(frank);
        userRepository.save(patrick);
        userRepository.save(mark);

        Book book1 = makeBook("This title", "Author", 334, 123.3);
        Book book2 = makeBook("second title", "second Author", 983, 723.3);
        Book book3 = makeBook("coucou", "Patrick", 33, 1223.3);
        Book book4 = makeBook("Hey :D", "Mark zucker", 4, 1.3);
        bookRepository.save(book1);
        bookRepository.save(book2);
        BookId id3 = bookRepository.save(book3);
        bookRepository.save(book4);

        book3.setId(id3);
        Review review1 = makeReview(id3, 33, "Frank");
        Review review2 = makeReview(id3, 12, "Patrick33");
        Review review3 = makeReview(id3, 87, "Mark");
        List<Review> reviewsBook3 = Arrays.asList(review1, review2, review3);

        reviewsBook3.forEach(book3::addReview);

        bookRepository.save(book3);


    }

    private Book makeBook(String title, String author, int numPage, double price) {
        Book book = new Book(
                new Characteristics(title, author, numPage),
                new Price(price)
        );
        book.setId(BookId.NOT_ASSIGNED);
        return book;
    }

    private Review makeReview(BookId bookId, int rating, String reviewerName) {
        return new Review(bookId, new UserId(reviewerName), new Rating(rating));
    }

    private User makeUser(String username, String firstName, String lastName, int age) {
        LocalDate birthdate = LocalDate.now().minusYears(age);
        UserInfo info = new UserInfo(firstName, lastName, birthdate);
        UserId userId = new UserId(username);
        return new User(userId, info);
    }
}
