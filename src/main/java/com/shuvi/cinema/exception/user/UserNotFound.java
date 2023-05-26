package com.shuvi.cinema.exception.user;

import com.shuvi.cinema.exception.BaseNotFoundException;

import java.util.UUID;

/**
 * @author Shuvi
 */
public class UserNotFound extends BaseNotFoundException {

    public UserNotFound(String message) {
        super(message);
    }

    private static final String USER_WITH_ID_NOT_FOUND = "User with id %s not found.";
    private static final String USER_WITH_USERNAME_NOT_FOUND = "User with username %s not found.";

    public static UserNotFound createById(UUID id) {
        return new UserNotFound(USER_WITH_ID_NOT_FOUND.formatted(id));
    }

    public static UserNotFound createByUsername(String username) {
        return new UserNotFound(USER_WITH_USERNAME_NOT_FOUND.formatted(username));
    }

}
