package com.shuvi.cinema.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shuvi.cinema.controller.dto.review.ReviewCreateRequest;
import com.shuvi.cinema.controller.dto.review.ReviewResponse;
import com.shuvi.cinema.entity.UserEntity;
import com.shuvi.cinema.repository.UserRepository;
import com.shuvi.cinema.service.api.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static com.shuvi.cinema.common.ResourceConstant.REVIEW_API_PATH;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Shuvi
 */
@SpringBootTest
@AutoConfigureMockMvc
public class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private UserRepository userRepository;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    public void setupMock() {
        UserEntity user = userRepository.findAll().stream().findFirst().orElseThrow();
        Mockito.when(userService.getCurrentUser()).thenReturn(user);
    }

    @Test
    @WithMockUser
    void createTest() throws Exception {
        String message = "Данный фильм по моему мнению довольно неплох.";
        int score = 1;
        UUID cinemaId = UUID.fromString("554f7afd-4bf3-493c-91d9-8677a39aa1b1");
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
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", equalTo(message)))
                .andExpect(jsonPath("$.cinemaId", equalTo(cinemaId.toString())))
                .andExpect(jsonPath("$.createdAt").isNumber())
                .andExpect(jsonPath("$.updatedAt").doesNotExist())
                .andExpect(jsonPath("$.score", equalTo(score)))
                .andExpect(jsonPath("$.author").exists());
    }

    @WithMockUser
    ReviewResponse create(String message, int score, UUID cinemaId) throws Exception {
        final ReviewCreateRequest review = ReviewCreateRequest.builder()
                .score(score)
                .message(message)
                .cinemaId(cinemaId)
                .build();

        final String body = mapper.writeValueAsString(review);

        final String response = this.mockMvc.perform(post(REVIEW_API_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", equalTo(message)))
                .andExpect(jsonPath("$.cinemaId", equalTo(cinemaId.toString())))
                .andExpect(jsonPath("$.createdAt").isNumber())
                .andExpect(jsonPath("$.updatedAt").doesNotExist())
                .andExpect(jsonPath("$.score", equalTo(score)))
                .andExpect(jsonPath("$.author").exists())
                .andReturn().getResponse().getContentAsString();

        return mapper.readValue(response, ReviewResponse.class);
    }

    @Test
    @WithMockUser
    void findByIdTest() throws Exception {
        final String message = "Это рецензия на фильм.";
        final int score = 10;
        final UUID cinemaId = UUID.fromString("554f7afd-4bf3-493c-91d9-8677a39aa1b1");

        final ReviewResponse reviewResponse = create(message, score, cinemaId);

        final String urlTemplate = String.format("%s/%s", REVIEW_API_PATH, reviewResponse.getId());

        mockMvc.perform(get(urlTemplate))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", equalTo(message)))
                .andExpect(jsonPath("$.cinemaId", equalTo(cinemaId.toString())))
                .andExpect(jsonPath("$.createdAt").isNumber())
                .andExpect(jsonPath("$.updatedAt").doesNotExist())
                .andExpect(jsonPath("$.score", equalTo(score)))
                .andExpect(jsonPath("$.author").exists());
    }

    @Test
    @WithMockUser
    void deleteByIdTest() throws Exception {
        final String message = "Tests message.";
        final int score = 5;
        final UUID cinemaId = UUID.fromString("554f7afd-4bf3-493c-91d9-8677a39aa1b1");

        final ReviewResponse reviewResponse = create(message, score, cinemaId);

        final String urlTemplate = String.format("%s/%s", REVIEW_API_PATH, reviewResponse.getId());
        mockMvc.perform(delete(urlTemplate))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

}
