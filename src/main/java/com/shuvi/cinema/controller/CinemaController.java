package com.shuvi.cinema.controller;

import com.shuvi.cinema.controller.dto.cinema.CinemaCreateRequest;
import com.shuvi.cinema.controller.dto.cinema.CinemaResponse;
import com.shuvi.cinema.exception.handler.dto.ApiError;
import com.shuvi.cinema.service.api.CinemaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public CinemaResponse create(@RequestBody @Valid CinemaCreateRequest createCinemaRequest) {
        return cinemaService.create(createCinemaRequest);
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Запрос на получение всех записей кино.")
    public List<CinemaResponse> findAll(
            @RequestParam(defaultValue = "0") int start,
            @RequestParam(defaultValue = "100") @Positive int size) {
        return cinemaService.findAll(start, size);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Запрос на получение записи по идентификатору.")
    public CinemaResponse find(@PathVariable UUID id) {
        return cinemaService.findById(id);
    }
}
