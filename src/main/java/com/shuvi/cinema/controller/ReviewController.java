package com.shuvi.cinema.controller;

import com.shuvi.cinema.controller.dto.review.ReviewCreateRequest;
import com.shuvi.cinema.controller.dto.review.ReviewResponse;
import com.shuvi.cinema.entity.ReviewEntity;
import com.shuvi.cinema.entity.UserEntity;
import com.shuvi.cinema.exception.AccessDenied;
import com.shuvi.cinema.service.api.ReviewService;
import com.shuvi.cinema.service.api.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

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
@Tag(name = "Review", description = "Manager Review")
public class ReviewController {

    private final UserService userService;

    private final ReviewService reviewService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewResponse create(@NonNull @Valid @RequestBody ReviewCreateRequest reviewCreateRequest) {
        return reviewService.create(reviewCreateRequest);
    }

    @GetMapping("/{id}")
    public ReviewResponse findById(@NonNull @PathVariable UUID id) {
        return reviewService.findById(id);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@NonNull @PathVariable UUID id) {
        final ReviewEntity reviewEntity = reviewService.getById(id);
        final UserEntity user = userService.getCurrentUser();

        // Проверка является ли пользователь автором рецензии.
        if (reviewEntity.getAuthor().getId().equals(user.getId())) {
            reviewService.deleteById(id);
            return;
        }

        // Проверка имеет ли пользователей права администратора.
        if (user.getRoles().stream().anyMatch((role) -> role.getName().equals("ROLE_ADMIN"))) {
            reviewService.deleteById(id);
            return;
        }

        throw new AccessDenied();
    }
}
