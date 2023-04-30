package com.shuvi.cinema.exception.handler.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

/**
 * DTO ошибки.
 *
 * @author Shuvi
 */
@Data
@Jacksonized
@SuperBuilder
public class ApiError {
    private String message;
    private LocalDateTime timestamp;

    public ApiError(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public ApiError(String message, LocalDateTime timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }
}

