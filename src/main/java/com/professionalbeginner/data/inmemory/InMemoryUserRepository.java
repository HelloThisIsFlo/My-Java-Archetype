package com.professionalbeginner.data.inmemory;

import com.professionalbeginner.domain.core.user.User;
import com.professionalbeginner.domain.core.user.UserId;
import com.professionalbeginner.domain.interfacelayer.repository.UserNotFoundException;
import com.professionalbeginner.domain.interfacelayer.repository.UserRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Kempenich Florian
 */
public class InMemoryUserRepository implements UserRepository {

    private Map<UserId, User> store = new HashMap<>();

    @Override
    public UserId save(User user) {
        checkNotNull(user);
        store.put(user.getUserId(), user);
        return user.getUserId();
    }

    @Override
    public User findByUsername(UserId username) throws UserNotFoundException {
        checkNotNull(username);
        if (!store.containsKey(username)) {
            throw new UserNotFoundException(username);
        }
        return store.get(username);
    }

}
