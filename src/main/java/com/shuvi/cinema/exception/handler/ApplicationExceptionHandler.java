package com.shuvi.cinema.exception.handler;

import com.shuvi.cinema.exception.BaseException;
import com.shuvi.cinema.exception.handler.dto.ApiError;
import lombok.extern.log4j.Log4j2;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

/**
 * @author Shuvi
 */
@Log4j2
@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiError> handleException(BaseException ex) {
        ResponseStatus responseStatus = ex.getClass().getAnnotation(ResponseStatus.class);
        String message = responseStatus.reason();
        int status = responseStatus.code().value();
        ApiError apiError = new ApiError(message, LocalDateTime.now());
        return ResponseEntity.status(status).body(apiError);
    }

    @ExceptionHandler({ConstraintViolationException.class, EmptyResultDataAccessException.class})
    public ResponseEntity<ApiError> handleSqlException(Exception ex) {
        // TODO: Возможно это приведёт к уязвимости сервера.
        ApiError apiError = new ApiError(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
    }


}
