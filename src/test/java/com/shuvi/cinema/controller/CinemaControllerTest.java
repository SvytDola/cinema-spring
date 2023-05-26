package com.shuvi.cinema.controller;

import com.shuvi.cinema.controller.dto.cinema.CinemaCreateRequest;
import com.shuvi.cinema.controller.dto.cinema.CinemaResponse;
import com.shuvi.cinema.entity.CinemaEntity;
import com.shuvi.cinema.entity.UserEntity;
import com.shuvi.cinema.repository.CinemaRepository;
import com.shuvi.cinema.repository.UserRepository;
import com.shuvi.cinema.service.api.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;
import java.util.UUID;

import static com.shuvi.cinema.common.ResourceConstant.CINEMA_API_PATH;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Тест контроллера кино.
 *
 * @author Shuvi
 */
public class CinemaControllerTest extends BaseIntegrationTest {

    @SpyBean
    private UserRepository userRepository;

    @MockBean
    private UserService userService;

    @Autowired
    private CinemaRepository cinemaRepository;

    @BeforeEach
    public void setupMock() {
        final UserEntity user = userRepository.findAll().stream().findFirst().orElseThrow();
        Mockito.when(userService.getCurrentUser()).thenReturn(user);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
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
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", equalTo(name)))
                .andExpect(jsonPath("$.description", equalTo(description)))
                .andExpect(jsonPath("$.duration", equalTo(Math.toIntExact(duration))))
                .andExpect(jsonPath("$.genres").isArray());
    }

    @Test
    void createWithEmptyNameAndDescriptionTest() throws Exception {
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
    void findAllTest() throws Exception {
        final int start = 0;
        final int size = 100;
        final String[] genres = {"horror"};

        final List<CinemaEntity> cinemas = cinemaRepository.findByGenresNameIn(List.of(genres), PageRequest.of(start, size));

        final Object[] names = cinemas.stream().map(CinemaEntity::getName).toArray();
        final Object[] descriptions = cinemas.stream().map(CinemaEntity::getDescription).toArray();
        final Object[] durations = cinemas.stream().map(cinema -> Math.toIntExact(cinema.getDuration())).toArray();

        mockMvc.perform(get(CINEMA_API_PATH)
                        .queryParam("start", String.valueOf(start))
                        .queryParam("size", String.valueOf(size))
                        .queryParam("genres", genres))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[*].name", containsInAnyOrder(names)))
                .andExpect(jsonPath("$[*].description", containsInAnyOrder(descriptions)))
                .andExpect(jsonPath("$[*].duration", containsInAnyOrder(durations)))
                .andExpect(jsonPath("$[*].genres").isArray());
    }


    private CinemaResponse createCinema(
            String name,
            String description,
            long duration,
            List<UUID> uuids) throws Exception {
        final CinemaCreateRequest cinemaCreateRequest = CinemaCreateRequest.builder()
                .name(name)
                .description(description)
                .duration(duration)
                .genres(uuids)
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
    void findByIdTest(
            String id,
            String name,
            String description,
            int duration) throws Exception {
        final String urlTemplate = String.format("%s/%s", CINEMA_API_PATH, id);

        mockMvc.perform(get(urlTemplate))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", equalTo(name)))
                .andExpect(jsonPath("$.description", equalTo(description)))
                .andExpect(jsonPath("$.duration", equalTo(Math.toIntExact(duration))))
                .andExpect(jsonPath("$.genres").isArray());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void deleteById() throws Exception {
        final String name = "Test";
        final String description = "test description";
        final long duration = 100;
        final List<UUID> genres = List.of();

        final CinemaResponse cinemaCreated = createCinema(name, description, duration, genres);

        final String urlTemplate = String.format("%s/%s", CINEMA_API_PATH, cinemaCreated.getId());

        mockMvc.perform(delete(urlTemplate))
                .andDo(print())
                .andExpect(status().isNoContent());

    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void updateById() throws Exception {
        final String name = "delete";
        final String description = "test description";
        final long duration = 100;
        final List<UUID> genreIds = List.of();

        final CinemaResponse cinemaCreated = createCinema(name, description, duration, genreIds);

        final String urlTemplate = String.format("%s/%s", CINEMA_API_PATH, cinemaCreated.getId());

        final String updatedName = "updated";
        final String updatedDescription = "updated";
        final long updatedDuration = 1000;
        final List<UUID> updatedGenres = List.of();

        final CinemaCreateRequest cinemaCreateRequest = CinemaCreateRequest.builder()
                .name(updatedName)
                .description(updatedDescription)
                .duration(updatedDuration)
                .genres(updatedGenres).build();

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
