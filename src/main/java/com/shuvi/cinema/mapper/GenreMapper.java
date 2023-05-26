package com.shuvi.cinema.mapper;

import com.shuvi.cinema.config.MappersConfig;
import com.shuvi.cinema.controller.dto.genre.GenreCreateRequest;
import com.shuvi.cinema.controller.dto.genre.GenreResponse;
import com.shuvi.cinema.entity.GenreEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.UUID;

/**
 * @author Shuvi
 */
@Mapper(config = MappersConfig.class)
public interface GenreMapper {
    GenreResponse toResponse(GenreEntity genreEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cinemas", ignore = true)
    GenreEntity toEntity(GenreCreateRequest createGenreRequest);

    @Mapping(target = "cinemas", ignore = true)
    GenreEntity toEntity(UUID id, GenreCreateRequest body);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cinemas", ignore = true)
    void update(@MappingTarget GenreEntity genreEntityFromDb, GenreEntity genreEntityUpdate);

    List<GenreResponse> toResponseList(List<GenreEntity> genreEntities);
}
