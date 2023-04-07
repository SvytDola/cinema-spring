package com.shuvi.cinema.exception.handler;

import com.shuvi.cinema.exception.GenreNotFound;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author Shuvi
 */
@Log4j2
@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({GenreNotFound.class})
    public void handleException() {
    }
}
