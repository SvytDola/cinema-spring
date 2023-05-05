package com.shuvi.cinema.service.impl;

import com.shuvi.cinema.controller.dto.genre.GenreCreateRequest;
import com.shuvi.cinema.controller.dto.genre.GenreResponse;
import com.shuvi.cinema.entity.GenreEntity;
import com.shuvi.cinema.exception.genre.GenreNotFound;
import com.shuvi.cinema.mapper.GenreMapper;
import com.shuvi.cinema.repository.GenreRepository;
import com.shuvi.cinema.service.api.GenreService;

import lombok.RequiredArgsConstructor;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author Shuvi
 */
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

    @Override
    public GenreResponse findById(@NonNull UUID id) {
        GenreEntity genreEntity = genreRepository.findById(id).orElseThrow(GenreNotFound::new);
        return genreMapper.toResponse(genreEntity);
    }

    @Override
    public void deleteById(@NonNull UUID id) {
        genreRepository.deleteById(id);
    }

    @Override
    public GenreResponse update(@NonNull UUID id, @NonNull GenreCreateRequest body) {
        GenreEntity genreEntityFromDb = genreRepository.findById(id).orElseThrow(GenreNotFound::new);
        GenreEntity genreEntityUpdate = genreMapper.toEntity(id, body);

        genreMapper.update(genreEntityFromDb, genreEntityUpdate);

        GenreEntity genreEntityUpdated = genreRepository.save(genreEntityFromDb);
        return genreMapper.toResponse(genreEntityUpdated);
    }

    @Override
    public List<GenreResponse> findAll() {
        List<GenreEntity> genreEntities = genreRepository.findAll();
        return genreMapper.toResponseList(genreEntities);
    }

    @Override
    public Set<GenreEntity> findAllByIds(@NonNull Set<UUID> uuids) {
        return genreRepository.findByIdIn(uuids);
    }
}
