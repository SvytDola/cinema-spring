package com.shuvi.cinema.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Shuvi
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Genre not found.")
public class GenreNotFound extends BaseException {
}
