package com.professionalbeginner.domain.interfacelayer.repository;

import com.professionalbeginner.domain.core.user.User;
import com.professionalbeginner.domain.core.user.UserId;

/**
 * @author Kempenich Florian
 */
public interface UserRepository {

    UserId save(User user);

    User findByUsername(UserId username) throws UserNotFoundException;


}
