package com.shuvi.cinema.service.impl;

import com.shuvi.cinema.controller.dto.review.ReviewCreateRequest;
import com.shuvi.cinema.controller.dto.review.ReviewResponse;
import com.shuvi.cinema.mapper.ReviewMapper;
import com.shuvi.cinema.service.api.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Реализация API сущности "Review".
 *
 * @author Shuvi
 */
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewMapper reviewMapper;

    @Override
    public ReviewResponse create(ReviewCreateRequest reviewCreateRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String username = authentication.getName();

//        ReviewEntity reviewEntity =
        return null;
    }
}
