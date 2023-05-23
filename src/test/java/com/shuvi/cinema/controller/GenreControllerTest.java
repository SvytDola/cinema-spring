package com.shuvi.cinema.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shuvi.cinema.controller.dto.genre.GenreCreateRequest;
import com.shuvi.cinema.controller.dto.genre.GenreResponse;
import com.shuvi.cinema.entity.GenreEntity;
import com.shuvi.cinema.entity.UserEntity;
import com.shuvi.cinema.repository.GenreRepository;
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

import static com.shuvi.cinema.common.ResourceConstant.GENRE_API_PATH;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Тест контроллера жанров.
 *
 * @author Shuvi
 */
@SpringBootTest
@AutoConfigureMockMvc
class GenreControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @SpyBean
    private UserRepository userRepository;

    @MockBean
    private UserService userService;

    @Autowired
    private GenreRepository genreRepository;


    @BeforeEach
    public void setupMock() {
        final UserEntity user = userRepository.findAll().stream().findFirst().orElseThrow();
        Mockito.when(userService.getCurrentUser()).thenReturn(user);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/db/changelog/v1.0.0/dml/data/genre.csv", numLinesToSkip = 1)
    void findByIdTest(String uuid, String name, String description) throws Exception {
        final String urlTemplate = String.format("%s/%s", GENRE_API_PATH, uuid);

        this.mockMvc.perform(get(urlTemplate))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", equalTo(uuid)))
                .andExpect(jsonPath("$.name", equalTo(name)))
                .andExpect(jsonPath("$.description", equalTo(description)));

    }

    @Test
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    void createTest() throws Exception {
        final String genreName = "sad";
        final String genreDescription = "Sad genre.";
        final GenreCreateRequest genreCreateRequest = GenreCreateRequest.builder()
                .name(genreName)
                .description(genreDescription)
                .build();

        final String body = mapper.writeValueAsString(genreCreateRequest);

        this.mockMvc.perform(post(GENRE_API_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", equalTo(genreName)))
                .andExpect(jsonPath("$.description", equalTo(genreDescription)));
    }

    @Test
    void createWithBlankNameAndDescriptionTest() throws Exception {
        final String genreName = "";
        final String genreDescription = "";
        final GenreCreateRequest genreCreateRequest = GenreCreateRequest.builder()
                .name(genreName)
                .description(genreDescription)
                .build();

        final String body = mapper.writeValueAsString(genreCreateRequest);

        mockMvc.perform(post(GENRE_API_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void findByNotExistIdTest() throws Exception {
        final String urlTemplate = String.format("%s/%s", GENRE_API_PATH, "3fa85f64-5717-4562-b3fc-2c963f66afa6");
        mockMvc.perform(get(urlTemplate)).andDo(print()).andExpect(status().isNotFound());
    }

    GenreResponse create(String name, String description) throws Exception {
        final GenreCreateRequest genreCreateRequest = GenreCreateRequest.builder()
                .name(name)
                .description(description)
                .build();
        final String body = mapper.writeValueAsString(genreCreateRequest);
        return mapper.readValue(
                mockMvc.perform(post(GENRE_API_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body))
                        .andExpect(status().isCreated())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.name", equalTo(name)))
                        .andExpect(jsonPath("$.description", equalTo(description)))
                        .andReturn().getResponse().getContentAsString(),
                GenreResponse.class);
    }

    GenreResponse updateById(String id, String name, String description) throws Exception {
        final GenreCreateRequest genreCreateRequest = GenreCreateRequest.builder()
                .name(name)
                .description(description)
                .build();

        final String body = mapper.writeValueAsString(genreCreateRequest);

        final String urlTemplate = String.format("%s/%s", GENRE_API_PATH, id);

        final String response = mockMvc.perform(put(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(id)))
                .andExpect(jsonPath("$.name", equalTo(name)))
                .andExpect(jsonPath("$.description", equalTo(description)))
                .andReturn().getResponse().getContentAsString();

        return mapper.readValue(response, GenreResponse.class);

    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void updateByIdTest() throws Exception {
        final String genreName = "test";
        final String genreDesc = "test description";
        final GenreResponse genreResponse = create(genreName, genreDesc);

        final String updateGenreName = genreName + "update";
        final String updateGenreDescription = genreDesc + "update";

        updateById(genreResponse.getId().toString(), updateGenreName, updateGenreDescription);

    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void createWithNotUniqueNameTest() throws Exception {
        final String name = "unique";
        final String description = "desc";

        create(name, description);

        final GenreCreateRequest genreCreateRequest = GenreCreateRequest.builder()
                .name(name)
                .description(description)
                .build();

        final String body = mapper.writeValueAsString(genreCreateRequest);

        this.mockMvc.perform(post(GENRE_API_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void deleteByIdTest() throws Exception {
        final String name = "delete";
        final String description = "desc";

        final GenreResponse genreCreated = create(name, description);

        final String urlTemplate = String.format("%s/%s", GENRE_API_PATH, genreCreated.getId());

        mockMvc.perform(delete(urlTemplate))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void deleteByNotExistIdTest() throws Exception {
        final String urlTemplate = String.format("%s/%s", GENRE_API_PATH, "3fa85f64-5717-4562-b3fc-2c963f66afa6");
        mockMvc.perform(delete(urlTemplate))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteByNotValidIdTest() throws Exception {
        final String urlTemplate = String.format("%s/%s", GENRE_API_PATH, "-1");

        mockMvc.perform(delete(urlTemplate))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void findAllTest() throws Exception {
        final List<GenreEntity> genres = genreRepository.findAll();

        final Object[] ids = genres.stream().map(genre -> genre.getId().toString()).toArray();
        final Object[] names = genres.stream().map(GenreEntity::getName).toArray();
        final Object[] descriptions = genres.stream().map(GenreEntity::getDescription).toArray();

        this.mockMvc.perform(get(GENRE_API_PATH))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(genres.size())))
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(ids)))
                .andExpect(jsonPath("$[*].name", containsInAnyOrder(names)))
                .andExpect(jsonPath("$[*].description", containsInAnyOrder(descriptions)));
    }
}
