package com.shuvi.cinema.service.api;


import com.shuvi.cinema.controller.dto.review.ReviewCreateRequest;
import com.shuvi.cinema.controller.dto.review.ReviewResponse;

/**
 * Сервис API сущности "Review".
 *
 * @author Shuvi
 */
public interface ReviewService {

    /**
     * Создать рецензию.
     *
     * @param reviewCreateRequest Детали создаваемой рецензии.
     * @return Информацию о созданной рецензии.
     */
    ReviewResponse create(ReviewCreateRequest reviewCreateRequest);
}
