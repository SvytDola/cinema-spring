package com.shuvi.cinema.exception.user;

import com.shuvi.cinema.exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Shuvi
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "User not found.")
public class UserNotFound extends BaseException {
}
