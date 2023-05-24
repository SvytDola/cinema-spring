package com.shuvi.cinema.exception.cinema;

import com.shuvi.cinema.exception.BaseNotFoundException;

import java.util.UUID;

public class CinemaNotFound extends BaseNotFoundException {

    public CinemaNotFound(String message) {
        super(message);
    }

    private static final String CINEMA_WITH_ID_NOT_FOUND = "Cinema with id %s not found.";

    public static CinemaNotFound createById(UUID id) {
        return new CinemaNotFound(CINEMA_WITH_ID_NOT_FOUND.formatted(id));
    }

}
