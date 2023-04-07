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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


/**
 * @author Shuvi
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/genre")
public class GenreController {

    private final GenreService genreService;

    @PostMapping("/")
    @ResponseStatus(CREATED)
    @Operation(summary = "Запрос на содание жанра.")
    public GenreResponse createGenre(@RequestBody @Valid GenreCreateRequest createRequest) {
        return genreService.createGenre(createRequest);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение жанра по идентификатору.", responses = {
            @ApiResponse(responseCode = "404", content = @Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ApiError.class)
            )),
            @ApiResponse(responseCode = "200", content = @Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = GenreResponse.class)
            ))
    })
    public GenreResponse findById(@NonNull @PathVariable UUID id) {
        return genreService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Удаление жанра по идентификатору.")
    public void deleteById(@NonNull @PathVariable UUID id) {
        genreService.deleteById(id);
    }

}
