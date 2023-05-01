package com.shuvi.cinema.exception.handler;

import com.shuvi.cinema.exception.BaseException;
import com.shuvi.cinema.exception.handler.dto.ApiError;
import lombok.extern.log4j.Log4j2;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Shuvi
 */
@Log4j2
@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiError> handleException(BaseException ex) {
        ResponseStatus responseStatus = ex.getClass().getAnnotation(ResponseStatus.class);
        String message = responseStatus.reason();
        int status = responseStatus.code().value();
        ApiError apiError = new ApiError(message, LocalDateTime.now());
        return ResponseEntity.status(status).body(apiError);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ApiError> handleEmptyResult(Exception ex) {
        // TODO: Возможно это приведёт к уязвимости сервера.
        ApiError apiError = new ApiError(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class, javax.validation.ConstraintViolationException.class})
    public ResponseEntity<ApiError> handleSqlException(Exception ex) {
        // TODO: Возможно это приведёт к уязвимости сервера.
        ApiError apiError = new ApiError(ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
    }


    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        // TODO: Возможно это приведёт к уязвимости сервера.
        ApiError apiError = new ApiError(Objects.requireNonNull(ex.getFieldError()).getDefaultMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(apiError);
    }

}
