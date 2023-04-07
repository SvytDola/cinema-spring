package com.shuvi.cinema.controller;

import com.shuvi.cinema.controller.dto.genre.GenreCreateRequest;
import com.shuvi.cinema.controller.dto.genre.GenreResponse;
import com.shuvi.cinema.service.api.GenreService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;


/**
 * @author Shuvi
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("genre")
public class GenreController {

    private final GenreService genreService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Запрос на содание жанра.")
    public GenreResponse createGenre(@RequestBody @Valid GenreCreateRequest createRequest) {
        return genreService.createGenre(createRequest);
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получение жанра по идентификатору.")
    public GenreResponse findById(@NonNull @PathVariable UUID id) {
        return genreService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удаление жанра по идентификатору.")
    public void deleteById(@NonNull @PathVariable UUID id) {
        genreService.deleteById(id);
    }

}
