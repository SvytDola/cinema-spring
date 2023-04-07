package com.shuvi.cinema.exception.handler.dto;

import lombok.Data;

@Data
public class ApiError {
    private String message;
    private int status;

    public ApiError(String message, int status) {
        this.message = message;
        this.status = status;
    }
}

