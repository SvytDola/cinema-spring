package com.shuvi.cinema.service.impl;

import com.shuvi.cinema.controller.dto.user.UserCreateRequest;
import com.shuvi.cinema.controller.dto.user.UserResponse;
import com.shuvi.cinema.entity.UserEntity;
import com.shuvi.cinema.exception.user.UserNotFound;
import com.shuvi.cinema.mapper.UserMapper;
import com.shuvi.cinema.repository.UserRepository;
import com.shuvi.cinema.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Реализация сервиса API сущности "User".
 *
 * @author Shuvi
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserEntity loadUserByUsername(String username) throws UsernameNotFoundException {
        final UserEntity userEntity = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));

        return userEntity;
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserByEmail(String email) {
        final UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(UserNotFound::new);
        return userMapper.toResponse(userEntity);
    }


    @Override
    @Transactional(readOnly = true)
    public UserResponse findById(@NonNull UUID id) {
        final UserEntity userEntity = userRepository.findById(id).orElseThrow(UserNotFound::new);
        return userMapper.toResponse(userEntity);
    }

    @Override
    public UserResponse create(@NonNull UserCreateRequest body) {
        UserEntity userEntity = userMapper.toEntity(body);

        userEntity.setEnabled(true);
        userEntity.setRoles(List.of());
        userEntity.setReviews(Set.of());

        UserEntity userCreated = userRepository.save(userEntity);
        return userMapper.toResponse(userCreated);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<UserResponse> findAll(int start, int size) {
        Collection<UserEntity> userEntities = userRepository.findAll(PageRequest.of(start, size)).toList();
        return userMapper.toResponseList(userEntities);
    }
}
