package com.professionalbeginner.data.hibernate;

import com.professionalbeginner.data.hibernate.model.ReviewJpaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Kempenich Florian
 */
@Repository
public interface HibernateCrudReviewRepository extends CrudRepository<ReviewJpaEntity, Long> {
}
