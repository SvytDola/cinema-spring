package com.shuvi.cinema.exception;

import org.springframework.http.HttpStatus;

public class BaseNotFoundException extends BaseException {
    public BaseNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND.value(), message);
    }
}
