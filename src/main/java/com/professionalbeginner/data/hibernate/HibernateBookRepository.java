package com.professionalbeginner.data.hibernate;

import com.professionalbeginner.data.hibernate.model.BookJpaEntity;
import com.professionalbeginner.domain.core.book.Book;
import com.professionalbeginner.domain.core.book.BookId;
import com.professionalbeginner.domain.interfacelayer.repository.BookRepository;
import com.professionalbeginner.spring.IntegrationTests;
import com.professionalbeginner.spring.Prod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Kempenich Florian
 */
@Prod
@IntegrationTests
@Repository
public class HibernateBookRepository implements BookRepository{

    private final JpaMapper jpaMapper;
    private final HibernateCrudBookRepository hibernateCrudBookRepository;

    @Autowired
    public HibernateBookRepository(JpaMapper jpaMapper, HibernateCrudBookRepository hibernateCrudBookRepository) {
        this.jpaMapper = jpaMapper;
        this.hibernateCrudBookRepository = hibernateCrudBookRepository;
    }

    @Override
    public BookId save(Book book) {
        BookJpaEntity jpaBook = jpaMapper.map(book);
        hibernateCrudBookRepository.save(jpaBook);

        Book saved = jpaMapper.map(jpaBook);
        return saved.id();
    }

    @Override
    public Book findById(BookId id, boolean withReviews) {
        BookJpaEntity fromDB = hibernateCrudBookRepository.findOne(id.idLong());

        return jpaMapper.map(fromDB);
    }

    @Override
    public List<Book> findAll(boolean withReviews) {
        throw new RuntimeException("Not yet implemented");
    }
}
