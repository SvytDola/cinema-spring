package com.shuvi.cinema.service.api;


import com.shuvi.cinema.controller.dto.review.ReviewCreateRequest;
import com.shuvi.cinema.controller.dto.review.ReviewResponse;
import com.shuvi.cinema.entity.ReviewEntity;
import org.springframework.lang.NonNull;

import java.util.UUID;

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
    ReviewResponse create(@NonNull ReviewCreateRequest reviewCreateRequest);

    /**
     * Поиск рецензии по идентификатору.
     *
     * @param id Идентификатор рецензии.
     * @return Информация о найденной рецензии.
     */
    ReviewResponse findById(@NonNull UUID id);

    /**
     * Поиск рецензии по идентификатору.
     *
     * @param id Идентификатор рецензии.
     * @return Информация о найденной рецензии.
     */
    ReviewEntity getById(@NonNull UUID id);

    /**
     * Удаление рецензии.
     *
     * @param id Идентификатор рецензии.
     */
    void deleteById(@NonNull UUID id);
}
