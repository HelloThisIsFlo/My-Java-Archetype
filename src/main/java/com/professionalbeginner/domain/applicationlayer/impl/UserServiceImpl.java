package com.professionalbeginner.domain.applicationlayer.impl;

import com.professionalbeginner.domain.applicationlayer.UserService;
import com.professionalbeginner.domain.applicationlayer.exception.ExistingUserException;
import com.professionalbeginner.domain.core.user.User;
import com.professionalbeginner.domain.core.user.UserId;
import com.professionalbeginner.domain.core.user.UserInfo;
import com.professionalbeginner.domain.interfacelayer.repository.UserNotFoundException;
import com.professionalbeginner.domain.interfacelayer.repository.UserRepository;
import org.springframework.stereotype.Service;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Kempenich Florian
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = checkNotNull(userRepository);
    }

    @Override
    public UserId createNewUser(UserId username, UserInfo userInfo) throws ExistingUserException {
        checkIfExistingUsername(username);
        User toSave = new User(username, userInfo);
        userRepository.save(toSave);
        return username;
    }

    private void checkIfExistingUsername(UserId username) throws ExistingUserException {
        try {
            userRepository.findByUsername(username);
            throw new ExistingUserException(username);
        } catch (UserNotFoundException e) {
            // expected
        }
    }

    @Override
    public User getDetails(UserId username) throws UserNotFoundException {
        return userRepository.findByUsername(username);
    }
}
