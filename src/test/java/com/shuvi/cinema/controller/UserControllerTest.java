package com.shuvi.cinema.controller;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.http.MediaType;

import static com.shuvi.cinema.common.ResourceConstant.USER_API_PATH;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Тест контроллера пользователей.
 *
 * @author Shuvi
 */
public class UserControllerTest extends BaseIntegrationTest {

    @ParameterizedTest
    @CsvFileSource(resources = "/db/changelog/v1.0.0/dml/data/users.csv", numLinesToSkip = 1)
    void findByIdTest(
            String id,
            String email,
            String name,
            String surname,
            String description,
            String password,
            Boolean enabled) throws Exception {

        final String urlTemplate = String.format("%s/%s", USER_API_PATH, id);

        mockMvc.perform(get(urlTemplate))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", equalTo(name)))
                .andExpect(jsonPath("$.id", equalTo(id)))
                .andExpect(jsonPath("$.surname", equalTo(surname)))
                .andExpect(jsonPath("$.description", equalTo(description)))
                .andExpect(jsonPath("$.enabled", equalTo(enabled)));
    }
}
