package com.shuvi.cinema.exception.handler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.shuvi.cinema.exception.BaseException;
import com.shuvi.cinema.exception.handler.dto.ApiError;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.shuvi.cinema.common.ExceptionMessageConstant.NOT_FOUND_MESSAGE;

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
        ResponseStatus responseStatus = ex.getClass().getAnnotation(ResponseStatus.class);
        String message = responseStatus.reason();
        int status = responseStatus.code().value();
        ApiError apiError = new ApiError(message, LocalDateTime.now());
        return ResponseEntity.status(status).body(apiError);
    }

    /**
     * Обработка исключения, которое появляетсяв в ходе удаления записи из таблицы по идентификатору,
     * которого не существует.
     */
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ApiError> handleEmptyResult(EmptyResultDataAccessException ex, WebRequest request) {
        String[] split = ((ServletWebRequest) request).getRequest().getPathInfo().split("/");
        String id = split[1];
        ApiError apiError = new ApiError(NOT_FOUND_MESSAGE.formatted(id));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
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
