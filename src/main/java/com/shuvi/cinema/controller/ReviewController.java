package com.shuvi.cinema.controller;

import com.shuvi.cinema.controller.dto.review.ReviewCreateRequest;
import com.shuvi.cinema.controller.dto.review.ReviewResponse;
import com.shuvi.cinema.service.api.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.shuvi.cinema.common.ResourceConstant.REVIEW_API_PATH;

/**
 * Контроллер API сущности "Genre".
 *
 * @author Shuvi
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(REVIEW_API_PATH)
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewResponse create(@NonNull @Valid @RequestBody ReviewCreateRequest reviewCreateRequest) {
        return reviewService.create(reviewCreateRequest);
    }
}
