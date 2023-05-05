package com.shuvi.cinema.service.api;

import com.shuvi.cinema.controller.dto.genre.GenreCreateRequest;
import com.shuvi.cinema.controller.dto.genre.GenreResponse;
import com.shuvi.cinema.entity.GenreEntity;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Сервис API сущности "Genre".
 *
 * @author Shuvi
 */
public interface GenreService {

    /**
     * Создать жанр.
     *
     * @param createGenreRequest Детали создаваемого жанра.
     * @return Информация о созданном событии.
     */
    GenreResponse createGenre(@NonNull GenreCreateRequest createGenreRequest);

    /**
     * Получить DTO по идентификатору жанра..
     *
     * @param id Идентификатор жанра.
     * @return Информация о жанре.
     */
    GenreResponse findById(@NonNull UUID id);

    /**
     * Удаление записи о жанре по идентификатору.
     *
     * @param id Идентфикатор жанра.
     */
    void deleteById(@NonNull UUID id);

    /**
     * Обновление данных о жанре по идентификатору.
     *
     * @param id   Идентфикатор жанра.
     * @param body Детали жанра.
     * @return Информацию об обновлённом жанре.
     */
    GenreResponse update(@NonNull UUID id, @NonNull GenreCreateRequest body);

    /**
     * Получить список всех жанров.
     *
     * @return Список жанров.
     */
    List<GenreResponse> findAll();

    /**
     * Получить список жанров по списку идентификаторов.
     *
     * @param uuids Список идентификаторов.
     * @return Список сущностей "Genre".
     */
    Set<GenreEntity> findAllByIds(@NonNull Set<UUID> uuids);
}
