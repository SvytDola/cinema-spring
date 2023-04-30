package com.shuvi.cinema.service.impl;

import com.shuvi.cinema.controller.dto.cinema.CinemaCreateRequest;
import com.shuvi.cinema.controller.dto.cinema.CinemaResponse;
import com.shuvi.cinema.entity.CinemaEntity;
import com.shuvi.cinema.mapper.CinemaMapper;
import com.shuvi.cinema.repository.CinemaRepository;
import com.shuvi.cinema.service.api.CinemaService;
import com.shuvi.cinema.service.api.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

/**
 * @author Shuvi
 */
@Service
@RequiredArgsConstructor
public class CinemaServiceImpl implements CinemaService {

    private final CinemaMapper cinemaMapper;

    private final GenreService genreService;

    private final CinemaRepository cinemaRepository;

    @Override
    public CinemaResponse create(@NotNull CinemaCreateRequest createCinemaRequest) {
        CinemaEntity cinemaEntity = cinemaMapper.toEntity(createCinemaRequest);

        cinemaEntity.setGenres(genreService.findAllByIds(createCinemaRequest.getGenres()));

        CinemaEntity created = cinemaRepository.save(cinemaEntity);
        return cinemaMapper.toResponse(created);
    }
}





















