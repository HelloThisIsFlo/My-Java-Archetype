package com.professionalbeginner.domain.interfacelayer.repository;

import com.professionalbeginner.domain.core.book.BookId;
import com.professionalbeginner.domain.core.user.UserId;

/**
 * @author Kempenich Florian
 */
public class UserNotFoundException extends Exception {

    private final UserId username;

    public UserNotFoundException(UserId username) {
        super("User could not be found: " + username);
        this.username = username;
    }

    public UserId getSearchedUsername() {
        return username;
    }
}
