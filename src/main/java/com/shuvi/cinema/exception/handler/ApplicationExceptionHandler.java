package com.shuvi.cinema.exception.handler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.shuvi.cinema.exception.BaseException;
import com.shuvi.cinema.exception.handler.dto.ApiError;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Objects;


/**
 * @author Shuvi
 */
@RestControllerAdvice
public class ApplicationExceptionHandler {

    /**
     * Обработка собственных исключений.
     */
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiError> handleException(BaseException ex) {
        ApiError apiError = new ApiError(ex.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(ex.getStatus()).body(apiError);
    }

    /**
     * Обработка исключений, связанных с некорректными данными для sql таблицы.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleSqlException(ConstraintViolationException ex) {
        String message = ex.getSQLException().getMessage().split("\n")[1].strip();
        ApiError apiError = new ApiError(message);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
    }

    /**
     * Обработка исключений, связанных с валидацией данных.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleArgumentException(MethodArgumentNotValidException ex) {
        String field = StringUtils.capitalize(Objects.requireNonNull(ex.getFieldError()).getField());
        String violation = Objects.requireNonNull(ex.getFieldError()).getDefaultMessage();
        ApiError apiError = new ApiError(String.format("%s %s.", field, violation));
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(apiError);
    }

    /**
     * Обработка исключениый валидации.
     */
    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ApiError> handleInvalidFormatException(InvalidFormatException invalidFormatException) {
        ApiError apiError = new ApiError(invalidFormatException.getMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(apiError);
    }

}
