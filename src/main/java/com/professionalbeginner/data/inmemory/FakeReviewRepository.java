package com.professionalbeginner.data.inmemory;

import com.professionalbeginner.domain.core.review.Review;
import com.professionalbeginner.domain.core.review.ReviewId;
import com.professionalbeginner.domain.interfacelayer.repository.ReviewRepository;

import java.util.Random;

/**
 * @author Kempenich Florian
 */
public class FakeReviewRepository implements ReviewRepository {

    private final Random random;

    public FakeReviewRepository() {
        this.random = new Random();
    }

    @Override
    public ReviewId save(Review review) {
        // do nothing
        return new ReviewId(new Long(random.nextInt(999) + 1));
    }
}
