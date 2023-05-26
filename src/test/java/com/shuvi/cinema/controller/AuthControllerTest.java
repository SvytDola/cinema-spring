package com.shuvi.cinema.controller;

import com.shuvi.cinema.controller.dto.user.UserCreateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static com.shuvi.cinema.common.ResourceConstant.AUTH_API_PATH;
import static com.shuvi.cinema.common.ResourceConstant.LOGIN_API_PATH;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Тесты для контроллера авторизации.
 *
 * @author Shuvi
 */
public class AuthControllerTest extends BaseIntegrationTest {

    @Test
    void loginTest() throws Exception {
        final String email = "example@gmail.com";
        final String password = "string";

        mockMvc.perform(post(LOGIN_API_PATH)
                        .queryParam("username", email)
                        .queryParam("password", password)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.access_token").isNotEmpty())
                .andExpect(jsonPath("$.refresh_token").isNotEmpty())
                .andExpect(jsonPath("$.user").isNotEmpty());
    }

    @Test
    void registerTest() throws Exception {
        final String email = "shuvi@gmail.com";
        final char[] password = "password".toCharArray();
        final String description = "desc";
        final String surname = "dola";
        final String name = "shuvi";

        final UserCreateRequest userCreateRequest = UserCreateRequest.builder()
                .description(description)
                .email(email)
                .password(password)
                .surname(surname)
                .name(name)
                .build();

        final String urlTemplate = String.format("%s/%s", AUTH_API_PATH, "register");

        final String body = mapper.writeValueAsString(userCreateRequest);

        mockMvc.perform(post(urlTemplate)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.access_token").isNotEmpty())
                .andExpect(jsonPath("$.refresh_token").isNotEmpty())
                .andExpect(jsonPath("$.user").isNotEmpty())
                .andExpect(jsonPath("$.user.name", equalTo(name)))
                .andExpect(jsonPath("$.user.surname", equalTo(surname)))
                .andExpect(jsonPath("$.user.description", equalTo(description)))
                .andExpect(jsonPath("$.user.enabled", equalTo(true)));
    }
}
