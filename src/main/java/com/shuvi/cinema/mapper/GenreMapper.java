package com.shuvi.cinema.mapper;

import com.shuvi.cinema.config.MappersConfig;
import com.shuvi.cinema.controller.dto.genre.GenreCreateRequest;
import com.shuvi.cinema.controller.dto.genre.GenreResponse;
import com.shuvi.cinema.entity.GenreEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.UUID;

/**
 * @author Shuvi
 */
@Mapper(config = MappersConfig.class)
public interface GenreMapper {
    GenreResponse toResponse(GenreEntity genreEntity);

    @Mapping(target = "id", ignore = true)
    GenreEntity toEntity(GenreCreateRequest createGenreRequest);

    GenreEntity toEntity(UUID id, GenreCreateRequest body);

    @Mapping(target = "id", ignore = true)
    void update(@MappingTarget GenreEntity genreEntityFromDb, GenreEntity genreEntityUpdate);
}
