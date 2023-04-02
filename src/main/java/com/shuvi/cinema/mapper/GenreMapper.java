package com.shuvi.cinema.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.shuvi.cinema.config.MappersConfig;

import com.shuvi.cinema.entity.GenreEntity;

import com.shuvi.cinema.controller.dto.genre.GenreResponse;
import com.shuvi.cinema.controller.dto.genre.GenreCreateRequest;


@Mapper(config = MappersConfig.class)
public interface GenreMapper {
    GenreResponse toResponse(GenreEntity genreEntity);

    @Mapping(target = "id", ignore = true)
    GenreEntity toEntity(GenreCreateRequest createGenreRequest);

}
