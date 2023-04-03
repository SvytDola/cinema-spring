package com.shuvi.cinema.service.impl;

import com.shuvi.cinema.controller.dto.genre.GenreCreateRequest;
import com.shuvi.cinema.controller.dto.genre.GenreResponse;
import com.shuvi.cinema.entity.GenreEntity;
import com.shuvi.cinema.mapper.GenreMapper;
import com.shuvi.cinema.repository.GenreRepository;
import com.shuvi.cinema.service.api.GenreService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

/**
 * @author Shuvi
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    @Override
    public GenreResponse createGenre(@NonNull GenreCreateRequest createGenreRequest) {
        GenreEntity genreEntity = genreMapper.toEntity(createGenreRequest);
        GenreEntity genreEntityCreated = genreRepository.save(genreEntity);
        return genreMapper.toResponse(genreEntityCreated);
    }
}
