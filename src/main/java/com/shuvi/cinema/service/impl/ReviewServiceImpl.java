package com.shuvi.cinema.service.impl;

import com.shuvi.cinema.controller.dto.review.ReviewCreateRequest;
import com.shuvi.cinema.controller.dto.review.ReviewResponse;
import com.shuvi.cinema.entity.CinemaEntity;
import com.shuvi.cinema.entity.ReviewEntity;
import com.shuvi.cinema.entity.UserEntity;
import com.shuvi.cinema.exception.AccessDenied;
import com.shuvi.cinema.exception.review.ReviewNotFound;
import com.shuvi.cinema.mapper.ReviewMapper;
import com.shuvi.cinema.repository.ReviewRepository;
import com.shuvi.cinema.service.api.CinemaService;
import com.shuvi.cinema.service.api.ReviewService;
import com.shuvi.cinema.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Реализация API сущности "Review".
 *
 * @author Shuvi
 */
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    private final ReviewMapper reviewMapper;

    private final CinemaService cinemaService;

    private final UserService userService;

    @Override
    @Transactional
    public ReviewResponse create(@NonNull ReviewCreateRequest reviewCreateRequest) {
        final UserEntity userEntity = userService.getCurrentUser();
        final CinemaEntity cinemaEntity = cinemaService.getById(reviewCreateRequest.getCinemaId());

        final ReviewEntity reviewEntity = reviewMapper.toEntity(reviewCreateRequest);

        reviewEntity.setAuthor(userEntity);
        reviewEntity.setCinema(cinemaEntity);
        reviewEntity.setCreatedAt(System.currentTimeMillis());

        final ReviewEntity reviewEntityCreated = reviewRepository.save(reviewEntity);
        return reviewMapper.toResponse(reviewEntityCreated);
    }

    @Override
    @Transactional
    public ReviewResponse findById(@NonNull UUID id) {
        final ReviewEntity reviewEntity = reviewRepository.findById(id).orElseThrow(ReviewNotFound::new);
        return reviewMapper.toResponse(reviewEntity);
    }

    @Override
    public void deleteById(@NonNull UUID id) {
        final ReviewEntity reviewEntity = reviewRepository.findById(id).orElseThrow(ReviewNotFound::new);
        final UserEntity user = userService.getCurrentUser();

        if (reviewEntity.getAuthor().getId().equals(user.getId())) {
            reviewRepository.deleteById(id);
        } else if (user.getRoles().stream().anyMatch((role) -> role.getName().equals("ROLE_ADMIN"))) {
            reviewRepository.deleteById(id);
        }

        throw new AccessDenied();
    }

}
