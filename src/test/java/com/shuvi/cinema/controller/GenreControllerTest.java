package com.shuvi.cinema.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shuvi.cinema.controller.dto.genre.GenreCreateRequest;
import com.shuvi.cinema.controller.dto.genre.GenreResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static com.shuvi.cinema.common.ResourceConstant.GENRE_API_PATH;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @ParameterizedTest
    @CsvFileSource(resources = "/db/changelog/v1.0.0/dml/data/genre.csv", numLinesToSkip = 1)
    void getGenreById(String uuid, String name, String description) throws Exception {
        String urlTemplate = String.format("%s/%s", GENRE_API_PATH, uuid);

        this.mockMvc.perform(get(urlTemplate))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(uuid)))
                .andExpect(jsonPath("$.name", equalTo(name)))
                .andExpect(jsonPath("$.description", equalTo(description)));

    }

    @Test
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    void testCreateGenre() throws Exception {
        String genreName = "sad";
        String genreDescription = "Sad genre.";
        GenreCreateRequest genreCreateRequest = GenreCreateRequest.builder()
                .name(genreName)
                .description(genreDescription)
                .build();

        String body = mapper.writeValueAsString(genreCreateRequest);

        this.mockMvc.perform(post(GENRE_API_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(genreName)))
                .andExpect(jsonPath("$.description", equalTo(genreDescription)));
    }

    @Test
    void testCreateGenreWithBlankNameAndDescription() throws Exception {
        String genreName = "";
        String genreDescription = "";
        GenreCreateRequest genreCreateRequest = GenreCreateRequest.builder()
                .name(genreName)
                .description(genreDescription)
                .build();

        String body = mapper.writeValueAsString(genreCreateRequest);

        mockMvc.perform(post(GENRE_API_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void testFindGenreByNotExistId() throws Exception {
        String urlTemplate = String.format("%s/%s", GENRE_API_PATH, "3fa85f64-5717-4562-b3fc-2c963f66afa6");
        mockMvc.perform(get(urlTemplate)).andDo(print()).andExpect(status().isNotFound());
    }

    GenreResponse createGenre(String name, String description) throws Exception {
        GenreCreateRequest genreCreateRequest = GenreCreateRequest.builder()
                .name(name)
                .description(description)
                .build();
        String body = mapper.writeValueAsString(genreCreateRequest);
        return mapper.readValue(
                mockMvc.perform(post(GENRE_API_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.name", equalTo(name)))
                        .andExpect(jsonPath("$.description", equalTo(description)))
                        .andReturn().getResponse().getContentAsString(),
                GenreResponse.class);
    }

    GenreResponse updateGenreById(String id, String name, String description) throws Exception {
        GenreCreateRequest genreCreateRequest = GenreCreateRequest.builder()
                .name(name)
                .description(description)
                .build();

        String body = mapper.writeValueAsString(genreCreateRequest);

        String urlTemplate = String.format("%s/%s", GENRE_API_PATH, id);

        String response = mockMvc.perform(put(urlTemplate)
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
    void testUpdateGenre() throws Exception {
        String genreName = "test";
        String genreDesc = "test description";
        GenreResponse genreResponse = createGenre(genreName, genreDesc);

        String updateGenreName = genreName + "update";
        String updateGenreDescription = genreDesc + "update";

        updateGenreById(genreResponse.getId().toString(), updateGenreName, updateGenreDescription);

    }

    @Test
    void testCreateGenreWithNotUniqueName() throws Exception {
        String name = "unique";
        String description = "desc";

        createGenre(name, description);

        GenreCreateRequest genreCreateRequest = GenreCreateRequest.builder()
                .name(name)
                .description(description)
                .build();

        String body = mapper.writeValueAsString(genreCreateRequest);

        this.mockMvc.perform(post(GENRE_API_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    void testDeleteGenreById() throws Exception {
        String name = "delete";
        String description = "desc";

        GenreResponse genreCreated = createGenre(name, description);

        String urlTemplate = String.format("%s/%s", GENRE_API_PATH, genreCreated.getId());

        mockMvc.perform(delete(urlTemplate))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteGenreByNotExistId() throws Exception {
        String urlTemplate = String.format("%s/%s", GENRE_API_PATH, "3fa85f64-5717-4562-b3fc-2c963f66afa6");
        mockMvc.perform(delete(urlTemplate))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteGenreByNotValidId() throws Exception {
        String urlTemplate = String.format("%s/%s", GENRE_API_PATH, "-1");

        mockMvc.perform(delete(urlTemplate))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}