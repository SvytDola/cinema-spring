package com.shuvi.cinema.exception;

import org.springframework.http.HttpStatus;

public class AccessDenied extends BaseException {
    public AccessDenied() {
        super(HttpStatus.FORBIDDEN.value(), "Access denied.");
    }
}
