package com.shuvi.cinema.mapper;

import com.shuvi.cinema.config.MappersConfig;
import com.shuvi.cinema.controller.dto.user.UserCreateRequest;
import com.shuvi.cinema.controller.dto.user.UserResponse;
import com.shuvi.cinema.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;
import java.util.List;

/**
 * @author Shuvi
 */
@Mapper(config = MappersConfig.class)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "password", ignore = true)
    UserEntity toEntity(UserCreateRequest body);

    UserResponse toResponse(UserEntity userCreated);

    List<UserResponse> toResponseList(Collection<UserEntity> userEntities);
}
