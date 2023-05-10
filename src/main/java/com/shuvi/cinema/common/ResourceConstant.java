package com.shuvi.cinema.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Константы ресурсов.
 *
 * @author Shuvi
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResourceConstant {

    /**
     * Жанры.
     */
    public static final String GENRE_API_PATH = "/api/v1/genre";

    /**
     * Кино.
     */
    public static final String CINEMA_API_PATH = "/api/v1/cinema";

    /**
     * Рецензии.
     */
    public static final String REVIEW_API_PATH = "/api/v1/review";

    /**
     * Авторизация.
     */
    public static final String AUTH_API_PATH = "/api/v1/auth";

    /**
     * Пользователи.
     */
    public static final String USER_API_PATH = "/api/v1/user";

    /**
     * Logout.
     */
    public static final String LOGOUT_API_PATH = AUTH_API_PATH + "/logout";

}
