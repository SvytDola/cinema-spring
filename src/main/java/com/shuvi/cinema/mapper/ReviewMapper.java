package com.shuvi.cinema.mapper;

import com.shuvi.cinema.config.MappersConfig;
import com.shuvi.cinema.controller.dto.review.ReviewCreateRequest;
import com.shuvi.cinema.controller.dto.review.ReviewResponse;
import com.shuvi.cinema.entity.ReviewEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Shuvi
 */
@Mapper(config = MappersConfig.class)
public interface ReviewMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "cinema", ignore = true)
    ReviewEntity toEntity(ReviewCreateRequest reviewCreateRequest);

    ReviewResponse toResponse(ReviewEntity reviewEntityCreated);
}
