package com.shuvi.cinema.controller;

import com.shuvi.cinema.controller.dto.cinema.CinemaCreateRequest;
import com.shuvi.cinema.controller.dto.cinema.CinemaResponse;
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

import static com.shuvi.cinema.common.ResourceConstant.CINEMA_API_PATH;

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
                    content = @Content(schema = @Schema(implementation = CinemaResponse.class))
            )
    })
    @ResponseStatus(HttpStatus.CREATED)
    public CinemaResponse create(@RequestBody @Valid CinemaCreateRequest createCinemaRequest) {
        return cinemaService.create(createCinemaRequest);
    }

}
