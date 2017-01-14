package com.professionalbeginner.data.hibernate;

import com.professionalbeginner.domain.core.review.Review;
import com.professionalbeginner.domain.core.review.ReviewId;
import com.professionalbeginner.domain.interfacelayer.repository.ReviewRepository;

/**
 * @author Kempenich Florian
 */
public class HibernateReviewRepository implements ReviewRepository {

    @Override
    public ReviewId save(Review review) {
        throw new RuntimeException("Not yet implemented");
    }
}
