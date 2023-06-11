package com.shuvi.cinema.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.ClassRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * @author Shuvi
 */
@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class BaseIntegrationTest {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    public ObjectMapper mapper;

    @ClassRule
    public static PostgreSQLContainer<CustomPSQLContainer> postgreSQLContainer = CustomPSQLContainer.getInstance();

}
