package com.shuvi.cinema.service.api;

import com.shuvi.cinema.controller.dto.genre.GenreCreateRequest;
import com.shuvi.cinema.controller.dto.genre.GenreResponse;
import org.springframework.lang.NonNull;

import java.util.UUID;

/**
 * @author Shuvi
 */
public interface GenreService {
    GenreResponse createGenre(@NonNull GenreCreateRequest createGenreRequest);

    GenreResponse findById(@NonNull UUID id);

    void deleteById(@NonNull UUID id);
}
