package com.shuvi.cinema.service.impl;

import com.shuvi.cinema.controller.dto.cinema.CinemaCreateRequest;
import com.shuvi.cinema.controller.dto.cinema.CinemaResponse;
import com.shuvi.cinema.entity.CinemaEntity;
import com.shuvi.cinema.entity.GenreEntity;
import com.shuvi.cinema.exception.cinema.CinemaNotFound;
import com.shuvi.cinema.mapper.CinemaMapper;
import com.shuvi.cinema.repository.CinemaRepository;
import com.shuvi.cinema.service.api.CinemaService;
import com.shuvi.cinema.service.api.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Реализация сервиса API сущности "Cinema".
 *
 * @author Shuvi
 */
@Service
@RequiredArgsConstructor
public class CinemaServiceImpl implements CinemaService {

    private final CinemaMapper cinemaMapper;

    private final GenreService genreService;

    private final CinemaRepository cinemaRepository;

    @Override
    public CinemaResponse create(@NonNull CinemaCreateRequest createCinemaRequest) {
        CinemaEntity cinemaEntity = cinemaMapper.toEntity(createCinemaRequest);
        Set<GenreEntity> genres = genreService.findAllByIds(createCinemaRequest.getGenres());
        cinemaEntity.setGenres(genres);
        CinemaEntity created = cinemaRepository.save(cinemaEntity);
        return cinemaMapper.toResponse(created);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CinemaResponse> findAll(int start, int size, @Nullable List<String> genres) {
        Pageable pageable = PageRequest.of(start, size);
        List<CinemaEntity> cinemaEntities;

        if (genres == null) {
            cinemaEntities = cinemaRepository.findAll(pageable).stream().toList();
        } else {
            cinemaEntities = cinemaRepository.findByGenresNameIn(genres, pageable);
        }

        return cinemaMapper.toResponseList(cinemaEntities);
    }

    @Override
    @Transactional(readOnly = true)
    public CinemaResponse findById(@NonNull UUID id) {
        CinemaEntity cinemaEntity = cinemaRepository.findById(id).orElseThrow(CinemaNotFound::new);
        return cinemaMapper.toResponse(cinemaEntity);
    }

    @Override
    public void deleteById(@NonNull UUID id) {
        cinemaRepository.deleteById(id);
    }

    @Override
    @Transactional
    public CinemaResponse updateById(@NonNull UUID id, @NonNull CinemaCreateRequest body) {
        CinemaEntity cinemaEntity = cinemaRepository.findById(id).orElseThrow(CinemaNotFound::new);
        Set<GenreEntity> genres = genreService.findAllByIds(body.getGenres());
        CinemaEntity cinemaUpdate = cinemaMapper.toEntity(body);
        cinemaUpdate.setGenres(genres);
        cinemaMapper.update(cinemaEntity, cinemaUpdate);
        CinemaEntity cinemaUpdated = cinemaRepository.save(cinemaEntity);
        return cinemaMapper.toResponse(cinemaUpdated);
    }
}