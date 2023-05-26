package com.shuvi.cinema.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author Shuvi
 */
@SpringBootTest
@AutoConfigureMockMvc
class BaseIntegrationTest {
    @Autowired
    public MockMvc mockMvc;

    @Autowired
    public ObjectMapper mapper;

}
