package com.shuvi.cinema.service.impl;

import com.shuvi.cinema.controller.dto.review.ReviewCreateRequest;
import com.shuvi.cinema.controller.dto.review.ReviewResponse;
import com.shuvi.cinema.entity.CinemaEntity;
import com.shuvi.cinema.entity.ReviewEntity;
import com.shuvi.cinema.entity.UserEntity;
import com.shuvi.cinema.exception.cinema.CinemaNotFound;
import com.shuvi.cinema.mapper.ReviewMapper;
import com.shuvi.cinema.repository.CinemaRepository;
import com.shuvi.cinema.repository.ReviewRepository;
import com.shuvi.cinema.service.api.ReviewService;
import com.shuvi.cinema.service.api.UserService;
import lombok.RequiredArgsConstructor;
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
    private final CinemaRepository cinemaRepository;

    private final UserService userService;

    @Override
    @Transactional
    public ReviewResponse create(ReviewCreateRequest reviewCreateRequest) {
        final UserEntity userEntity = userService.getCurrentUser();
        final CinemaEntity cinemaEntity = cinemaRepository.findById(UUID.fromString(reviewCreateRequest.getCinemaId()))
                .orElseThrow(CinemaNotFound::new);

        ReviewEntity reviewEntity = reviewMapper.toEntity(reviewCreateRequest);
        reviewEntity.setAuthor(userEntity);
        reviewEntity.setCinema(cinemaEntity);
        reviewEntity.setCreatedAt(System.currentTimeMillis());
        reviewEntity.setUpdatedAt(null);

        ReviewEntity reviewEntityCreated = reviewRepository.save(reviewEntity);
        return reviewMapper.toResponse(reviewEntityCreated);
    }
}
