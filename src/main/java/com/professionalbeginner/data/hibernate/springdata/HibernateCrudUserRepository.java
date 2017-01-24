package com.professionalbeginner.data.hibernate.springdata;

import com.professionalbeginner.data.hibernate.user.UserJpaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Kempenich Florian
 */
@Repository
public interface HibernateCrudUserRepository extends CrudRepository<UserJpaEntity, Long> {

    UserJpaEntity findByUsername(String username);
}
