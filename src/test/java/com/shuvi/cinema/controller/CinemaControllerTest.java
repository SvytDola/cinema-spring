package com.shuvi.cinema.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shuvi.cinema.controller.dto.cinema.CinemaCreateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static com.shuvi.cinema.common.ResourceConstant.CINEMA_API_PATH;
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
public class CinemaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void shouldCreateCinemaAndReturnStatus201() throws Exception {
        String name = "Cinema";
        String description = "Description";
        long duration = 100;
        Set<UUID> uuidSet = new HashSet<>();

        CinemaCreateRequest cinemaCreateRequest = CinemaCreateRequest.builder()
                .name(name)
                .description(description)
                .duration(duration)
                .genres(uuidSet)
                .build();

        String body = mapper.writeValueAsString(cinemaCreateRequest);

        mockMvc.perform(post(CINEMA_API_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(name)))
                .andExpect(jsonPath("$.description", equalTo(description)))
                .andExpect(jsonPath("$.duration", equalTo(Math.toIntExact(duration))))
                .andExpect(jsonPath("$.genres").isArray());
    }

    @Test
    void shouldReturnStatus() throws Exception {
        String name = "";
        String description = "";
        long duration = 100;
        Set<UUID> uuidSet = new HashSet<>();

        CinemaCreateRequest cinemaCreateRequest = CinemaCreateRequest.builder()
                .name(name)
                .description(description)
                .duration(duration)
                .genres(uuidSet)
                .build();

        String body = mapper.writeValueAsString(cinemaCreateRequest);

        mockMvc.perform(post(CINEMA_API_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                ).andDo(print())
                .andExpect(status().is5xxServerError());
    }

}
