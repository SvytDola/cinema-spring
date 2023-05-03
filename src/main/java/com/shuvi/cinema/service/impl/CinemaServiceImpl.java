package com.shuvi.cinema.service.impl;

import com.shuvi.cinema.controller.dto.cinema.CinemaCreateRequest;
import com.shuvi.cinema.controller.dto.cinema.CinemaResponse;
import com.shuvi.cinema.entity.CinemaEntity;
import com.shuvi.cinema.exception.CinemaNotFound;
import com.shuvi.cinema.mapper.CinemaMapper;
import com.shuvi.cinema.repository.CinemaRepository;
import com.shuvi.cinema.service.api.CinemaService;
import com.shuvi.cinema.service.api.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

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

    @Override
    @Transactional(readOnly = true)
    public List<CinemaResponse> findAll(int start, int size, List<String> genres) {
        List<CinemaEntity> cinemaEntities = cinemaRepository.findByGenresNameIn(genres, PageRequest.of(start, size));
        return cinemaMapper.toResponseList(cinemaEntities);
    }

    @Override
    @Transactional(readOnly = true)
    public CinemaResponse findById(UUID id) {
        CinemaEntity cinemaEntity = cinemaRepository.findById(id).orElseThrow(CinemaNotFound::new);
        return cinemaMapper.toResponse(cinemaEntity);
    }
}