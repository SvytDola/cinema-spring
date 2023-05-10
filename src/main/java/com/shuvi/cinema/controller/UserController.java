package com.shuvi.cinema.controller;

import com.shuvi.cinema.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.shuvi.cinema.common.ResourceConstant.USER_API_PATH;

/**
 * Контроллер API сущности "User".
 *
 * @author Shuvi
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(USER_API_PATH)
public class UserController {

    private final UserService userService;

}
