package com.shuvi.cinema.controller;

import com.shuvi.cinema.controller.dto.genre.GenreCreateRequest;
import com.shuvi.cinema.controller.dto.genre.GenreResponse;
import com.shuvi.cinema.exception.handler.dto.ApiError;
import com.shuvi.cinema.service.api.GenreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.util.List;
import java.util.UUID;

import static com.shuvi.cinema.common.ResourceConstant.GENRE_API_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


/**
 * Контроллер API сущности "Genre".
 *
 * @author Shuvi
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(GENRE_API_PATH)
public class GenreController {

    private final GenreService genreService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Запрос на содание жанра.", responses = {
            @ApiResponse(responseCode = "201", content = @Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = GenreResponse.class)
            )),
            @ApiResponse(responseCode = "409",
                    description = "Если создать жанр с неуникальным именем.",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class)
                    ))
    })
    public GenreResponse createGenre(@NonNull @RequestBody @Valid GenreCreateRequest createRequest) {
        return genreService.createGenre(createRequest);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получение жанра по идентификатору.", responses = {
            @ApiResponse(responseCode = "404",
                    description = "Если получить жанр по не существующему идентификатору.",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class)
                    )),
            @ApiResponse(
                    responseCode = "200",
                    description = "Ok.",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = GenreResponse.class))
            ),
            @ApiResponse(responseCode = "500",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class)),
                    description = "Если попытаться удалить жанр, которого не существует."
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "При не валидном идентификаторе.",
                    content = @Content(schema = @Schema()))

    })
    public GenreResponse findById(@NonNull @PathVariable UUID id) {
        return genreService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удаление жанра по идентификатору.", responses = {
            @ApiResponse(responseCode = "204", description = "Ok.", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "404",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class)),
                    description = "Если попытаться удалить жанр, которого не существует."
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "При не валидном идентификаторе.",
                    content = @Content(schema = @Schema())
            )
    })
    public void deleteById(@NonNull @PathVariable UUID id) {
        genreService.deleteById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Обновление жанра по идентификатору.", responses = {
            @ApiResponse(responseCode = "200",
                    description = "Ok.",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = GenreResponse.class)
                    )),
            @ApiResponse(responseCode = "404",
                    description = "Возникает, если попытаться обновить данные жанра, которого не существует.",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "При не валидном идентификаторе.",
                    content = @Content(schema = @Schema())
            ),
            @ApiResponse(responseCode = "409",
                    description = "Если создать жанр с неуникальным именем.",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class)
                    ))
    })
    public GenreResponse update(@NonNull @PathVariable UUID id, @NonNull @Valid @RequestBody GenreCreateRequest body) {
        return genreService.update(id, body);
    }

    @GetMapping
    @Operation(summary = "Возвращает список жанров.")
    public List<GenreResponse> findAll() {
        return genreService.findAll();
    }
}
