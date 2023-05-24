package com.shuvi.cinema.controller;

import com.shuvi.cinema.repository.UserRepository;
import com.shuvi.cinema.service.api.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;

import static com.shuvi.cinema.common.ResourceConstant.LOGIN_API_PATH;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Shuvi
 */
public class AuthControllerTest extends BaseIntegrationTest {

    @SpyBean
    private UserRepository userRepository;

    @MockBean
    private UserService userService;


    @Test
    void loginTest() throws Exception {
        final String email = "example@gmail.com";
        final String password = "string";

        String body = String.format("?username=%s&password=%s", email, password);

        mockMvc.perform(post(LOGIN_API_PATH + body)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
