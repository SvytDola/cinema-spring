package com.shuvi.cinema.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shuvi.cinema.controller.dto.cinema.CinemaCreateRequest;
import com.shuvi.cinema.controller.dto.cinema.CinemaResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static com.shuvi.cinema.common.ResourceConstant.CINEMA_API_PATH;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Shuvi
 */
public class UserControllerTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void createTest() throws Exception {
        final String name = "Cinema";
        final String description = "Description";
        final long duration = 100;
        final List<UUID> genres = List.of();

        final CinemaCreateRequest cinemaCreateRequest = CinemaCreateRequest.builder()
                .name(name)
                .description(description)
                .duration(duration)
                .genres(genres)
                .build();

        final String body = mapper.writeValueAsString(cinemaCreateRequest);

        mockMvc.perform(post(CINEMA_API_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(name)))
                .andExpect(jsonPath("$.description", equalTo(description)))
                .andExpect(jsonPath("$.duration", equalTo(Math.toIntExact(duration))))
                .andExpect(jsonPath("$.genres").isArray());
    }

    @Test
    void createWithBlankNameAndDescription() throws Exception {
        final String name = "";
        final String description = "";
        final long duration = 100;
        final List<UUID> genres = List.of();

        final CinemaCreateRequest cinemaCreateRequest = CinemaCreateRequest.builder()
                .name(name)
                .description(description)
                .duration(duration)
                .genres(genres)
                .build();

        final String body = mapper.writeValueAsString(cinemaCreateRequest);

        mockMvc.perform(post(CINEMA_API_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)).andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void getInfoByGenreNames() throws Exception {
        mockMvc.perform(get(String.format("%s?start=0&size=100", CINEMA_API_PATH)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getById() throws Exception {
        final String name = "test";
        final String description = "description";
        final long duration = 100;
        final List<UUID> genres = List.of();

        final CinemaResponse cinemaCreated = createCinema(name, description, duration, genres);

        final String urlTemplate = String.format("%s/%s", CINEMA_API_PATH, cinemaCreated.getId());

        mockMvc.perform(get(urlTemplate))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(name)))
                .andExpect(jsonPath("$.description", equalTo(description)))
                .andExpect(jsonPath("$.duration", equalTo(Math.toIntExact(duration))))
                .andExpect(jsonPath("$.genres").isArray());
    }

    private CinemaResponse createCinema(
            String name,
            String description,
            long duration,
            List<UUID> genres) throws Exception {
        final CinemaCreateRequest cinemaCreateRequest = CinemaCreateRequest.builder()
                .name(name)
                .description(description)
                .duration(duration)
                .genres(genres)
                .build();

        final String body = mapper.writeValueAsString(cinemaCreateRequest);

        return mapper.readValue(
                mockMvc.perform(post(CINEMA_API_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body)).andDo(print())
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.name", equalTo(name)))
                        .andExpect(jsonPath("$.description", equalTo(description)))
                        .andExpect(jsonPath("$.duration", equalTo(Math.toIntExact(duration))))
                        .andExpect(jsonPath("$.genres").isArray())
                        .andReturn().getResponse().getContentAsString(),
                CinemaResponse.class);
    }

    @CsvFileSource(resources = {"/db/changelog/v1.0.0/dml/data/cinema.csv"}, numLinesToSkip = 1)
    @ParameterizedTest
    void getInfoByIdTest(
            String id,
            String name,
            String description,
            int duration) throws Exception {
        final String urlTemplate = String.format("%s/%s", CINEMA_API_PATH, id);

        mockMvc.perform(get(urlTemplate))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(name)))
                .andExpect(jsonPath("$.description", equalTo(description)))
                .andExpect(jsonPath("$.duration", equalTo(Math.toIntExact(duration))))
                .andExpect(jsonPath("$.genres").isArray());
    }

    @Test
    void deleteById() throws Exception {
        final String name = "Test";
        final String description = "test description";
        final long duration = 100;
        final List<UUID> genres = List.of();

        final CinemaResponse cinemaCreated = createCinema(name, description, duration, genres);

        final String urlTemplate = String.format("%s/%s", CINEMA_API_PATH, cinemaCreated.getId());

        mockMvc.perform(delete(urlTemplate)).andDo(print())
                .andExpect(status().isNoContent());

    }

    @Test
    void updateById() throws Exception {
        final String name = "delete";
        final String description = "test description";
        final long duration = 100;
        final List<UUID> genres = List.of();

        final CinemaResponse cinemaCreated = createCinema(name, description, duration, genres);

        final String urlTemplate = String.format("%s/%s", CINEMA_API_PATH, cinemaCreated.getId());

        final String updatedName = "updated";
        final String updatedDescription = "updated";
        final long updatedDuration = 1000;
        final List<UUID> updatedGenres = List.of();

        final CinemaCreateRequest cinemaCreateRequest = CinemaCreateRequest.builder()
                .name(updatedName)
                .description(updatedDescription)
                .duration(updatedDuration)
                .genres(updatedGenres)
                .build();

        final String body = mapper.writeValueAsString(cinemaCreateRequest);

        mockMvc.perform(put(urlTemplate).contentType(MediaType.APPLICATION_JSON).content(body))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(updatedName)))
                .andExpect(jsonPath("$.description", equalTo(updatedDescription)))
                .andExpect(jsonPath("$.duration", equalTo(Math.toIntExact(updatedDuration))))
                .andExpect(jsonPath("$.genres").isArray());

    }
}
