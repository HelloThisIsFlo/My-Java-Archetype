package com.professionalbeginner.domain.core.user;

/**
 * @author Kempenich Florian
 */
public class IllegalUserException extends RuntimeException {

    private final UserInfo userInfo;

    public IllegalUserException(String message, UserInfo userInfo) {
        super(message);
        this.userInfo = userInfo;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }
}
