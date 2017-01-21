package com.professionalbeginner.domain.applicationlayer;

import com.professionalbeginner.domain.applicationlayer.exception.ExistingUserException;
import com.professionalbeginner.domain.core.user.User;
import com.professionalbeginner.domain.core.user.UserId;
import com.professionalbeginner.domain.core.user.UserInfo;
import com.professionalbeginner.domain.interfacelayer.repository.UserNotFoundException;

/**
 * @author Kempenich Florian
 */
public interface UserService {

    /**
     * Creates a new user and return the username (unchanged).
     * @return Username of the created user => Unchanged
     */
    UserId createNewUser(UserId username, UserInfo userInfo) throws ExistingUserException;

    /**
     * Retrieves the details of a particular user
     */
    User getDetails(UserId username) throws UserNotFoundException;
}
