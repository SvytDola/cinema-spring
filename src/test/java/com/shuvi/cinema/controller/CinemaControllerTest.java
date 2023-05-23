package com.shuvi.cinema.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shuvi.cinema.controller.dto.cinema.CinemaCreateRequest;
import com.shuvi.cinema.controller.dto.cinema.CinemaResponse;
import com.shuvi.cinema.entity.UserEntity;
import com.shuvi.cinema.repository.UserRepository;
import com.shuvi.cinema.service.api.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
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
@SpringBootTest
@AutoConfigureMockMvc
public class CinemaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;


    @SpyBean
    private UserRepository userRepository;

    @MockBean
    private UserService userService;


    @BeforeEach
    public void setupMock() {
        UserEntity user = userRepository.findAll().stream().findFirst().orElseThrow();
        Mockito.when(userService.getCurrentUser()).thenReturn(user);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void createTest() throws Exception {
        String name = "Cinema";
        String description = "Description";
        long duration = 100;
        List<UUID> uuidSet = List.of();

        CinemaCreateRequest cinemaCreateRequest = CinemaCreateRequest.builder()
                .name(name)
                .description(description)
                .duration(duration)
                .genres(uuidSet)
                .build();

        String body = mapper.writeValueAsString(cinemaCreateRequest);

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
        String name = "";
        String description = "";
        long duration = 100;
        List<UUID> uuidSet = List.of();

        CinemaCreateRequest cinemaCreateRequest = CinemaCreateRequest.builder()
                .name(name)
                .description(description)
                .duration(duration)
                .genres(uuidSet)
                .build();

        String body = mapper.writeValueAsString(cinemaCreateRequest);

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
    @WithMockUser(roles = {"ADMIN"})
    void getById() throws Exception {
        String name = "test";
        String description = "description";
        long duration = 100;
        List<UUID> uuids = List.of();

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
            List<UUID> uuids) throws Exception {
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
        String urlTemplate = String.format("%s/%s", CINEMA_API_PATH, id);

        mockMvc.perform(get(urlTemplate))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(name)))
                .andExpect(jsonPath("$.description", equalTo(description)))
                .andExpect(jsonPath("$.duration", equalTo(Math.toIntExact(duration))))
                .andExpect(jsonPath("$.genres").isArray());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void deleteById() throws Exception {
        String name = "Test";
        String description = "test description";
        long duration = 100;
        List<UUID> uuids = List.of();

        CinemaResponse cinemaCreated = createCinema(name, description, duration, uuids);

        String urlTemplate = String.format("%s/%s", CINEMA_API_PATH, cinemaCreated.getId());

        mockMvc.perform(delete(urlTemplate)).andDo(print())
                .andExpect(status().isNoContent());

    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void updateById() throws Exception {
        String name = "delete";
        String description = "test description";
        long duration = 100;
        List<UUID> genreIds = List.of();

        CinemaResponse cinemaCreated = createCinema(name, description, duration, genreIds);

        String urlTemplate = String.format("%s/%s", CINEMA_API_PATH, cinemaCreated.getId());

        String updatedName = "updated";
        String updatedDescription = "updated";
        long updatedDuration = 1000;
        List<UUID> updatedUuids = List.of();

        CinemaCreateRequest cinemaCreateRequest = CinemaCreateRequest.builder()
                .name(updatedName)
                .description(updatedDescription)
                .duration(updatedDuration)
                .genres(updatedUuids).build();

        String body = mapper.writeValueAsString(cinemaCreateRequest);

        mockMvc.perform(put(urlTemplate).contentType(MediaType.APPLICATION_JSON).content(body))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(updatedName)))
                .andExpect(jsonPath("$.description", equalTo(updatedDescription)))
                .andExpect(jsonPath("$.duration", equalTo(Math.toIntExact(updatedDuration))))
                .andExpect(jsonPath("$.genres").isArray());

    }
}
