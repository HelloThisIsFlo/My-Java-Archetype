package com.professionalbeginner.data.hibernate;

import com.professionalbeginner.data.hibernate.model.ReviewJpaEntity;
import com.professionalbeginner.domain.core.review.Review;
import com.professionalbeginner.domain.core.review.ReviewId;
import com.professionalbeginner.domain.interfacelayer.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author Kempenich Florian
 */
@Repository
public class HibernateReviewRepository implements ReviewRepository {

    private final HibernateCrudReviewRepository hibernateCrudReviewRepository;
    private final JpaMapper jpaMapper;

    @Autowired
    public HibernateReviewRepository(HibernateCrudReviewRepository hibernateCrudReviewRepository, JpaMapper jpaMapper) {
        this.hibernateCrudReviewRepository = hibernateCrudReviewRepository;
        this.jpaMapper = jpaMapper;
    }

    @Override
    public ReviewId save(Review review) {
        ReviewJpaEntity reviewJpa = jpaMapper.map(review);
        hibernateCrudReviewRepository.save(reviewJpa);

        return jpaMapper.mapId(reviewJpa);
    }
}
