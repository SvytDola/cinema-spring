package com.shuvi.cinema.service.api;

import com.shuvi.cinema.controller.dto.genre.GenreCreateRequest;
import com.shuvi.cinema.controller.dto.genre.GenreResponse;
import org.springframework.lang.NonNull;

/**
 * @author Shuvi
 */
public interface GenreService {
    GenreResponse createGenre(@NonNull GenreCreateRequest createGenreRequest);
}
