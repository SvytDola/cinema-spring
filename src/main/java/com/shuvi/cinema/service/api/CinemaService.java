package com.shuvi.cinema.service.api;

import com.shuvi.cinema.controller.dto.cinema.CinemaCreateRequest;
import com.shuvi.cinema.controller.dto.cinema.CinemaResponse;
import com.shuvi.cinema.entity.CinemaEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

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
    CinemaResponse create(@NonNull CinemaCreateRequest createCinemaRequest);

    /**
     * Получить список кино.
     *
     * @param start Точка старта откуда начинать брать данные из таблицы.
     * @param size  Количество записей, которое необходимо получить.
     * @return Список кино.
     */
    List<CinemaResponse> findAll(int start, int size, @Nullable List<String> genres);

    /**
     * Получить кино по идентификатору.
     *
     * @param id Идентификатор кино.
     * @return Информация о кино.
     */
    CinemaResponse findById(@NonNull UUID id);

    /**
     * Удалить кино по идентификатору.
     *
     * @param id Идентификатор кино.
     */
    void deleteById(@NonNull UUID id);

    /**
     * Обновление кино по иденитификатору.
     *
     * @param id   Идентификатор кино.
     * @param body Детали обновляемого кино.
     * @return Информацию об обновлённом кино.
     */
    CinemaResponse updateById(@NonNull UUID id, @NonNull CinemaCreateRequest body);

    /**
     * Получение кино по идентификатору.
     *
     * @param id Идентификатор кино.
     * @return Сущность кино.
     */
    CinemaEntity getById(@NonNull UUID id);
}
