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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
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
    public CinemaResponse create(@Valid @RequestBody CinemaCreateRequest createCinemaRequest) {
        return cinemaService.create(createCinemaRequest);
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Запрос на получение всех записей кино.", parameters = {
            @Parameter(name = "start", description = "Номер записи, с которой начинать поиск."),
            @Parameter(name = "size", description = "Количество записей, которое необходимо вернуть."),
            @Parameter(name = "genres", description = "Название жанров, по которым будет поиск.")
    })
    public List<CinemaResponse> findAll(
            @RequestParam(defaultValue = "0") int start,
            @RequestParam(defaultValue = "25") @Positive int size,
            @Null @RequestParam(required = false) List<String> genres) {
        return cinemaService.findAll(start, size, genres);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Запрос на получение записи по идентификатору.")
    public CinemaResponse find(@NotNull @PathVariable UUID id) {
        return cinemaService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удаление кино по идентификатору.")
    public void deleteById(@NotNull @PathVariable UUID id) {
        cinemaService.deleteById(id);
    }

}
