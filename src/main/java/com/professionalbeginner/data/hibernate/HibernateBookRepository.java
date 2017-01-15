package com.professionalbeginner.data.hibernate;

import com.professionalbeginner.data.hibernate.model.BookJpaEntity;
import com.professionalbeginner.data.hibernate.springdata.HibernateCrudBookRepository;
import com.professionalbeginner.domain.core.book.Book;
import com.professionalbeginner.domain.core.book.BookId;
import com.professionalbeginner.domain.interfacelayer.repository.BookNotFoundException;
import com.professionalbeginner.domain.interfacelayer.repository.BookRepository;
import com.professionalbeginner._other.spring.IntegrationTests;
import com.professionalbeginner._other.spring.Prod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kempenich Florian
 */
@Prod
@IntegrationTests
@Repository
public class HibernateBookRepository implements BookRepository {

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

        return jpaMapper.mapId(jpaBook);
    }

    @Override
    @Transactional // Aspect-oriented annotation that wraps the method call in a transaction
    public Book findById(BookId id, boolean withReviews) throws BookNotFoundException {
        BookJpaEntity fromDB = hibernateCrudBookRepository.findOne(id.idLong());
        if (fromDB == null) {
            throw new BookNotFoundException(id);
        }

        return jpaMapper.map(fromDB, withReviews);
    }

    @Override
    @Transactional // Aspect-oriented annotation that wraps the method call in a transaction
    public List<Book> findAll(boolean withReviews) {
        List<BookJpaEntity> fromDB = new ArrayList<>();
        hibernateCrudBookRepository.findAll().forEach(fromDB::add);

        return jpaMapper.mapAllToDomain(fromDB, withReviews);
    }
}
