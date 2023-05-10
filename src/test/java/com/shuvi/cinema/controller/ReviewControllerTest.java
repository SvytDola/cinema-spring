package com.shuvi.cinema.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shuvi.cinema.controller.dto.review.ReviewCreateRequest;
import com.shuvi.cinema.entity.UserEntity;
import com.shuvi.cinema.service.api.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static com.shuvi.cinema.common.ResourceConstant.REVIEW_API_PATH;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Shuvi
 */
@SpringBootTest
@AutoConfigureMockMvc
public class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private UserService userService;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    void configure() {
        Mockito.when(userService.getCurrentUser())
                .thenReturn(UserEntity.builder()
                        .name("SHuvi")
                        .email("local@gmail.com")
                        .password("12345678")
                        .surname("dola")
                        .id(UUID.randomUUID())
                        .description("desc")
                        .enabled(true)
                        .build()
                );
    }

    @Test
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    void createTest() throws Exception {
        String message = "daviod";
        int score = 1;
        String cinemaId = "554f7afd-4bf3-493c-91d9-8677a39aa1b1";
        ReviewCreateRequest review = ReviewCreateRequest.builder()
                .score(score)
                .message(message)
                .cinemaId(cinemaId)
                .build();

        String body = mapper.writeValueAsString(review);

        this.mockMvc.perform(post(REVIEW_API_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message", equalTo(message)))
                .andExpect(jsonPath("$.cinemaId", equalTo(cinemaId)));
    }

}
