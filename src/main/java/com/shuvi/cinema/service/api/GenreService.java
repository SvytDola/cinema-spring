package com.shuvi.cinema.service.api;

import org.springframework.lang.NonNull;

import com.shuvi.cinema.controller.dto.genre.GenreCreateRequest;
import com.shuvi.cinema.controller.dto.genre.GenreResponse;

/**
 * @author Shuvi
 */
public interface GenreService {
    GenreResponse createGenre(@NonNull GenreCreateRequest createGenreRequest);

}
