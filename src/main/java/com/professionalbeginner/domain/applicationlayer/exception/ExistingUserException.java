package com.professionalbeginner.domain.applicationlayer.exception;

import com.professionalbeginner.domain.core.user.UserId;

/**
 * @author Kempenich Florian
 */
public class ExistingUserException extends Exception {

    private final UserId existingUsername;

    public ExistingUserException(UserId existingUsername) {
        super("Username already existing");
        this.existingUsername = existingUsername;
    }

    public UserId getExistingUsername() {
        return existingUsername;
    }
}
