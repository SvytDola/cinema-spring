package com.shuvi.cinema.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shuvi.cinema.controller.dto.cinema.CinemaCreateRequest;
import com.shuvi.cinema.controller.dto.cinema.CinemaResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    void createTest() throws Exception {
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
    void createWithBlankNameAndDescription() throws Exception {
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
        String name = "test";
        String description = "description";
        long duration = 100;
        Set<UUID> uuids = new HashSet<>();

        CinemaResponse cinemaCreated = createCinema(name, description, duration, uuids);

        String urlTemplate = String.format("%s/%s", CINEMA_API_PATH, cinemaCreated.getId());

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
            Set<UUID> uuids
    ) throws Exception {
        CinemaCreateRequest cinemaCreateRequest = CinemaCreateRequest.builder()
                .name(name)
                .description(description)
                .duration(duration)
                .genres(uuids)
                .build();

        String body = mapper.writeValueAsString(cinemaCreateRequest);

        return mapper.readValue(
                mockMvc.perform(post(CINEMA_API_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body)
                        ).andDo(print())
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.name", equalTo(name)))
                        .andExpect(jsonPath("$.description", equalTo(description)))
                        .andExpect(jsonPath("$.duration", equalTo(Math.toIntExact(duration))))
                        .andExpect(jsonPath("$.genres").isArray())
                        .andReturn().getResponse().getContentAsString(),
                CinemaResponse.class
        );
    }

    @CsvFileSource(resources = {"/db/changelog/v1.0.0/dml/data/cinema.csv"}, numLinesToSkip = 1)
    @ParameterizedTest
    void getInfoByIdTest(
            String id,
            String name,
            String description,
            int duration) throws Exception {
        String urlTemplate = String.format("%s/%s", CINEMA_API_PATH, id);


        mockMvc.perform(get(urlTemplate))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(name)))
                .andExpect(jsonPath("$.description", equalTo(description)))
                .andExpect(jsonPath("$.duration", equalTo(Math.toIntExact(duration))))
                .andExpect(jsonPath("$.genres").isArray());
    }

}
