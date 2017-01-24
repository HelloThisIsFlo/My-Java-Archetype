package com.professionalbeginner.data.hibernate;

import com.professionalbeginner._other.spring.IntegrationTests;
import com.professionalbeginner._other.spring.Prod;
import com.professionalbeginner.data.hibernate.springdata.HibernateCrudUserRepository;
import com.professionalbeginner.data.hibernate.user.UserJpaEntity;
import com.professionalbeginner.data.hibernate.user.UserJpaMapper;
import com.professionalbeginner.domain.core.user.User;
import com.professionalbeginner.domain.core.user.UserId;
import com.professionalbeginner.domain.interfacelayer.repository.UserNotFoundException;
import com.professionalbeginner.domain.interfacelayer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Kempenich Florian
 */
@Prod
@IntegrationTests
@Repository
public class HibernateUserRepository implements UserRepository {

    private final HibernateCrudUserRepository hibernateCrudUserRepository;
    private final UserJpaMapper jpaMapper;

    @Autowired
    public HibernateUserRepository(HibernateCrudUserRepository hibernateCrudUserRepository, UserJpaMapper jpaMapper) {
        this.hibernateCrudUserRepository = hibernateCrudUserRepository;
        this.jpaMapper = jpaMapper;
    }

    @Override
    public UserId save(User user) {
        checkNotNull(user);

        UserJpaEntity jpaEntity = getJpaEntity(user);
        hibernateCrudUserRepository.save(jpaEntity);

        return user.getUserId();
    }

    private UserJpaEntity getJpaEntity(User user) {
        UserJpaEntity jpaEntity = jpaMapper.map(user);

        // Use existing id if user existing in database
        UserJpaEntity existing = hibernateCrudUserRepository.findByUsername(user.getUserId().username());
        if (existing != null) {
            jpaEntity.setId(existing.getId());
        }

        return jpaEntity;
    }

    @Override
    public User findByUsername(UserId username) throws UserNotFoundException {
        checkNotNull(username);

        UserJpaEntity fromDb = hibernateCrudUserRepository.findByUsername(username.username());
        if (fromDb == null) {
            throw new UserNotFoundException(username);
        }

        return jpaMapper.map(fromDb);
    }
}
