package com.professionalbeginner.domain.interfacelayer.repository;

import com.professionalbeginner.domain.core.review.Review;
import com.professionalbeginner.domain.core.review.ReviewId;

/**
 * @author Kempenich Florian
 */
public interface ReviewRepository {

    ReviewId save(Review review);

}
