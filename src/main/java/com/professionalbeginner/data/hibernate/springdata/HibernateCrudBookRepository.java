package com.professionalbeginner.data.hibernate.springdata;

import com.professionalbeginner.data.hibernate.book.BookJpaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Kempenich Florian
 */
@Repository
public interface HibernateCrudBookRepository extends CrudRepository<BookJpaEntity, Long> {



}
