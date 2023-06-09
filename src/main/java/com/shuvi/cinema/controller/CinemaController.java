package com.shuvi.cinema.controller;

import com.shuvi.cinema.controller.dto.cinema.CinemaCreateRequest;
import com.shuvi.cinema.controller.dto.cinema.CinemaResponse;
import com.shuvi.cinema.exception.handler.dto.ApiError;
import com.shuvi.cinema.service.api.CinemaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.UUID;

import static com.shuvi.cinema.common.ResourceConstant.CINEMA_API_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Контроллер API сущности "Cinema".
 *
 * @author Shuvi
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(CINEMA_API_PATH)
public class CinemaController {

    private final CinemaService cinemaService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    @Operation(summary = "Запрос на создание кино.", responses = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Ok.",
                    content = @Content(
                            schema = @Schema(implementation = CinemaResponse.class),
                            mediaType = APPLICATION_JSON_VALUE
                    )
            ),
            @ApiResponse(
                    responseCode = "422",
                    description = "При невалидном теле запроса.",
                    content = @Content(schema = @Schema(implementation = ApiError.class),
                            mediaType = APPLICATION_JSON_VALUE
                    )
            )
    })
    @ResponseStatus(HttpStatus.CREATED)
    public CinemaResponse create(
            @NonNull @Valid @RequestBody CinemaCreateRequest createCinemaRequest
    ) {
        return cinemaService.create(createCinemaRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Запрос на получение всех записей кино.", parameters = {
            @Parameter(name = "start", description = "Номер записи, с которой начинать поиск."),
            @Parameter(name = "size", description = "Количество записей, которое необходимо вернуть.")
    })
    public List<CinemaResponse> findAll(
            @RequestParam(defaultValue = "0") @Min(value = 0) int start,
            @RequestParam(defaultValue = "25") @Max(value = 100) @Positive int size,
            @RequestParam(required = false) List<String> genres) {
        return cinemaService.findAll(start, size, genres);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Запрос на получение записи по идентификатору.")
    public CinemaResponse findById(@NonNull @PathVariable UUID id) {
        return cinemaService.findById(id);
    }

    @PreAuthorize("isAuthenticated() && hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удаление кино по идентификатору.")
    public void deleteById(@NonNull @PathVariable UUID id) {
        cinemaService.deleteById(id);
    }

    @PreAuthorize("isAuthenticated() && hasRole('ADMIN')")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Обновление данных о кино.")
    public CinemaResponse updateById(
            @NonNull @PathVariable UUID id,
            @NonNull @Valid @RequestBody CinemaCreateRequest body) {
        return cinemaService.updateById(id, body);
    }


}
