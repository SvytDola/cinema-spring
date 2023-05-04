package com.shuvi.cinema.exception.cinema;

import com.shuvi.cinema.exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Cinema not found.")
public class CinemaNotFound extends BaseException {
}
