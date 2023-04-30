package com.shuvi.cinema.service.api;

import com.shuvi.cinema.controller.dto.cinema.CinemaCreateRequest;
import com.shuvi.cinema.controller.dto.cinema.CinemaResponse;

import javax.validation.constraints.NotNull;

/**
 * Сервис API сущности "Cinema".
 *
 * @author Shuvi
 */
public interface CinemaService {

    /**
     * Создать кино.
     *
     * @param createCinemaRequest Детали создаваемого кино.
     * @return Информацию о созданном кино.
     */
    CinemaResponse create(@NotNull CinemaCreateRequest createCinemaRequest);
}
