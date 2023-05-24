package com.shuvi.cinema.exception.review;

import com.shuvi.cinema.exception.BaseNotFoundException;

import java.util.UUID;

/**
 * @author Shuvi
 */
public class ReviewNotFound extends BaseNotFoundException {
    public ReviewNotFound(String message) {
        super(message);
    }

    private static final String REVIEW_WITH_ID_NOT_FOUND = "Review with id %s not found.";

    public static ReviewNotFound createById(UUID id) {
        return new ReviewNotFound(REVIEW_WITH_ID_NOT_FOUND.formatted(id));
    }

}
