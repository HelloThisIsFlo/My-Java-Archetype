package com.professionalbeginner.data.hibernate;

import com.professionalbeginner._other.spring.IntegrationTests;
import com.professionalbeginner._other.spring.Prod;
import com.professionalbeginner.domain.core.book.IllegalReviewException;
import com.professionalbeginner.domain.core.user.User;
import com.professionalbeginner.domain.core.user.UserId;
import com.professionalbeginner.domain.interfacelayer.repository.UserNotFoundException;
import com.professionalbeginner.domain.interfacelayer.repository.UserRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Kempenich Florian
 */
@Prod
@IntegrationTests
@Repository
public class HibernateUserRepository implements UserRepository {

    @Override
    public UserId save(User user) {
        throw new IllegalReviewException("Not implemented");
    }

    @Override
    public User findByUsername(UserId username) throws UserNotFoundException {
        throw new IllegalReviewException("Not implemented");
    }
}
