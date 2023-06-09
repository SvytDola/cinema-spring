package com.shuvi.cinema.mapper;

import com.shuvi.cinema.config.MappersConfig;
import com.shuvi.cinema.controller.dto.cinema.CinemaCreateRequest;
import com.shuvi.cinema.controller.dto.cinema.CinemaResponse;
import com.shuvi.cinema.entity.CinemaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * @author Shuvi
 */
@Mapper(config = MappersConfig.class, uses = ReviewMapper.class)
public interface CinemaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "genres", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    CinemaEntity toEntity(CinemaCreateRequest createCinemaRequest);

    CinemaResponse toResponse(CinemaEntity created);

    List<CinemaResponse> toResponseList(List<CinemaEntity> cinemas);

    @Mapping(target = "id", ignore = true)
    void update(@MappingTarget CinemaEntity cinemaEntity, CinemaEntity cinemaUpdate);
}
