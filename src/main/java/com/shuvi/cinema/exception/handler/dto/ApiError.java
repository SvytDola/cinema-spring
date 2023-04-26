package com.shuvi.cinema.exception.handler.dto;

import lombok.Data;

/**
 * @author Shuvi
 */
@Data
public class ApiError {
    private String message;

    public ApiError(String message) {
        this.message = message;
    }
}

