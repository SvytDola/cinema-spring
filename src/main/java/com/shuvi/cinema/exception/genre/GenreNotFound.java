package com.shuvi.cinema.exception.genre;

import com.shuvi.cinema.exception.BaseNotFoundException;

import java.util.UUID;

/**
 * @author Shuvi
 */
public class GenreNotFound extends BaseNotFoundException {
    public GenreNotFound(String message) {
        super(message);
    }

    private static final String GENRE_WITH_ID_NOT_FOUND = "Genre with id %s not found.";

    public static GenreNotFound createById(UUID id) {
        return new GenreNotFound(GENRE_WITH_ID_NOT_FOUND.formatted(id));
    }

}
