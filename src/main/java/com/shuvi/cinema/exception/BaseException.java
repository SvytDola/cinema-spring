package com.shuvi.cinema.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Shuvi
 */
@Getter
@RequiredArgsConstructor
public class BaseException extends RuntimeException {

    private final int status;
    private final String message;
}
