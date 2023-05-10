package com.shuvi.cinema.service.api;


import com.shuvi.cinema.controller.dto.review.ReviewCreateRequest;
import com.shuvi.cinema.controller.dto.review.ReviewResponse;

/**
 * Сервис API сущности "Review".
 *
 * @author Shuvi
 */
public interface ReviewService {
    ReviewResponse create(ReviewCreateRequest reviewCreateRequest);
}
