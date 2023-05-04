package com.shuvi.cinema.service.api;

import com.shuvi.cinema.controller.dto.cinema.CinemaCreateRequest;
import com.shuvi.cinema.controller.dto.cinema.CinemaResponse;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

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

    /**
     * Получить список кино.
     *
     * @param start  Точка старта откуда начинать брать данные из таблицы.
     * @param size   Количество записей, которое необходимо получить.
     * @param genres Список жанров.
     * @return Список кино.
     */
    List<CinemaResponse> findAll(int start, int size, List<String> genres);

    /**
     * Получить кино по идентификатору.
     *
     * @param id Идентификатор кино.
     * @return Информация о кино.
     */
    CinemaResponse findById(@NotNull UUID id);
}
