package com.shuvi.cinema.service.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.shuvi.cinema.controller.dto.cinema.CinemaCreateRequest;
import com.shuvi.cinema.controller.dto.cinema.CinemaResponse;
import com.shuvi.cinema.entity.CinemaEntity;
import com.shuvi.cinema.entity.GenreEntity;
import com.shuvi.cinema.exception.cinema.CinemaNotFound;
import com.shuvi.cinema.mapper.CinemaMapper;
import com.shuvi.cinema.mapper.factory.CinemaExpressionFactory;
import com.shuvi.cinema.repository.CinemaRepository;
import com.shuvi.cinema.service.api.CinemaService;
import com.shuvi.cinema.service.api.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    private final CinemaExpressionFactory cinemaExpressionFactory;

    @Override
    public CinemaResponse create(@NonNull CinemaCreateRequest createCinemaRequest) {
        final CinemaEntity cinemaEntity = cinemaMapper.toEntity(createCinemaRequest);
        final List<GenreEntity> genres = genreService.findAllByIds(createCinemaRequest.getGenres());
        cinemaEntity.setGenres(genres);
        final CinemaEntity created = cinemaRepository.save(cinemaEntity);
        return cinemaMapper.toResponse(created);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CinemaResponse> findAll(int start, int size, @Nullable List<String> genres) {
        final Pageable pageable = PageRequest.of(start, size);
        BooleanExpression booleanExp = cinemaExpressionFactory.getBooleanExp(genres);
        final List<CinemaEntity> cinemaEntities = cinemaRepository.findAll(booleanExp, pageable).stream().toList();
        return cinemaMapper.toResponseList(cinemaEntities);
    }

    @Override
    @Transactional(readOnly = true)
    public CinemaResponse findById(@NonNull UUID id) {
        final CinemaEntity cinemaEntity = getById(id);
        return cinemaMapper.toResponse(cinemaEntity);
    }

    @Override
    public void deleteById(@NonNull UUID id) {
        try {
            cinemaRepository.deleteById(id);
        } catch (EmptyResultDataAccessException exception) {
            throw CinemaNotFound.createById(id);
        }
    }

    @Override
    @Transactional
    public CinemaResponse updateById(@NonNull UUID id, @NonNull CinemaCreateRequest body) {
        final CinemaEntity cinemaEntity = getById(id);
        final List<GenreEntity> genres = genreService.findAllByIds(body.getGenres());

        final CinemaEntity cinemaUpdate = cinemaMapper.toEntity(body);
        cinemaUpdate.setGenres(genres);

        cinemaMapper.update(cinemaEntity, cinemaUpdate);

        final CinemaEntity cinemaUpdated = cinemaRepository.save(cinemaEntity);
        return cinemaMapper.toResponse(cinemaUpdated);
    }

    @Override
    public CinemaEntity getById(@NonNull UUID id) {
        return cinemaRepository.findById(id).orElseThrow(() -> CinemaNotFound.createById(id));
    }

}