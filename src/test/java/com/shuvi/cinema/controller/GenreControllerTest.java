package com.shuvi.cinema.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shuvi.cinema.controller.dto.genre.GenreCreateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class GenreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void testCreateGenre() throws Exception {
        String urlTemplate = "/genre";
        String genreName = "funny";
        String genreDescription = "Funny genre.";
        GenreCreateRequest genreCreateRequest = GenreCreateRequest.builder()
                .name(genreName)
                .description(genreDescription)
                .build();

        String body = mapper.writeValueAsString(genreCreateRequest);

        this.mockMvc.perform(post(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(genreName)))
                .andExpect(jsonPath("$.description", equalTo(genreDescription)));
    }

    @Test
    void testCreateGenreWithBlankNameAndDescription() throws Exception {
        String urlTemplate = "/genre";
        String genreName = "";
        String genreDescription = "";
        GenreCreateRequest genreCreateRequest = GenreCreateRequest.builder()
                .name(genreName)
                .description(genreDescription)
                .build();

        String body = mapper.writeValueAsString(genreCreateRequest);

        this.mockMvc.perform(post(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void testFindGenreByNotExistId() throws Exception {
        String urlTemplate = "/genre/3fa85f64-5717-4562-b3fc-2c963f66afa6";
        this.mockMvc.perform(get(urlTemplate)).andDo(print()).andExpect(status().isNotFound());
    }
}