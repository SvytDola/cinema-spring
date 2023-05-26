package com.shuvi.cinema.service.impl;

import com.shuvi.cinema.controller.dto.genre.GenreCreateRequest;
import com.shuvi.cinema.controller.dto.genre.GenreResponse;
import com.shuvi.cinema.entity.GenreEntity;
import com.shuvi.cinema.exception.genre.GenreNotFound;
import com.shuvi.cinema.mapper.GenreMapper;
import com.shuvi.cinema.repository.GenreRepository;
import com.shuvi.cinema.service.api.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Реализация сервиса API сущности "Genre".
 *
 * @author Shuvi
 */
@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    @Override
    public GenreResponse createGenre(@NonNull GenreCreateRequest createGenreRequest) {
        final GenreEntity genreEntity = genreMapper.toEntity(createGenreRequest);
        final GenreEntity genreEntityCreated = genreRepository.save(genreEntity);
        return genreMapper.toResponse(genreEntityCreated);
    }

    @Override
    public GenreEntity getById(UUID id) {
        return genreRepository.findById(id).orElseThrow(
                () -> GenreNotFound.createById(id)
        );
    }

    @Override
    public GenreResponse findById(@NonNull UUID id) {
        GenreEntity genreEntity = getById(id);
        return genreMapper.toResponse(genreEntity);
    }

    @Override
    public void deleteById(@NonNull UUID id) {
        try {
            genreRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw GenreNotFound.createById(id);
        }
    }

    @Override
    public GenreResponse updateById(@NonNull UUID id, @NonNull GenreCreateRequest body) {
        final GenreEntity genreEntityFromDb = getById(id);
        final GenreEntity genreEntityUpdate = genreMapper.toEntity(id, body);

        genreMapper.update(genreEntityFromDb, genreEntityUpdate);

        final GenreEntity genreEntityUpdated = genreRepository.save(genreEntityFromDb);
        return genreMapper.toResponse(genreEntityUpdated);
    }

    @Override
    public List<GenreResponse> findAll() {
        final List<GenreEntity> genreEntities = genreRepository.findAll();
        return genreMapper.toResponseList(genreEntities);
    }

    @Override
    public List<GenreEntity> findAllByIds(List<UUID> uuids) {
        return genreRepository.findByIdIn(uuids);
    }
}
