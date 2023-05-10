package com.shuvi.cinema.controller;


import com.shuvi.cinema.controller.dto.auth.AuthLoginRequest;
import com.shuvi.cinema.controller.dto.auth.AuthResponse;
import com.shuvi.cinema.controller.dto.user.UserCreateRequest;
import com.shuvi.cinema.service.api.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.shuvi.cinema.common.ResourceConstant.AUTH_API_PATH;

/**
 * Контроллер API сущности "Cinema".
 *
 * @author Shuvi
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(AUTH_API_PATH)
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse register(@NonNull @Valid @RequestBody UserCreateRequest body) {
        return authService.register(body);
    }

    @PostMapping(value = "/login", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public AuthResponse login(@NonNull @Valid AuthLoginRequest body) {
        return authService.login(body);
    }


}
