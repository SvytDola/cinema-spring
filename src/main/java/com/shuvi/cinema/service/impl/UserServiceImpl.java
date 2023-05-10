package com.shuvi.cinema.service.impl;

import com.shuvi.cinema.controller.dto.user.UserCreateRequest;
import com.shuvi.cinema.controller.dto.user.UserResponse;
import com.shuvi.cinema.entity.UserEntity;
import com.shuvi.cinema.exception.user.UserNotFound;
import com.shuvi.cinema.mapper.UserMapper;
import com.shuvi.cinema.repository.UserRepository;
import com.shuvi.cinema.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        return userRepository.findById(UUID.fromString(id)).orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }

    @Override
    @Transactional
    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(UserNotFound::new);
    }

    @Override
    public UserResponse create(@NonNull UserCreateRequest body) {
        UserEntity userEntity = userMapper.toEntity(body);

        userEntity.setRoles(List.of());
        userEntity.setEnabled(true);
        userEntity.setReviews(Set.of());

        UserEntity userCreated = userRepository.save(userEntity);
        return userMapper.toResponse(userCreated);
    }
}
