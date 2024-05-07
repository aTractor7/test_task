package com.example.controllers;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
public class DatesValidationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testWrongStartEndDates() throws Exception {
        String json = """
                {
                    "start": "2005-11-01",
                    "end": "2003-11-01"
                }
                """;

        mockMvc.perform(get("/users/dates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Error caused by field start - Start date should be before end date.;")));
    }

    @Test
    void testWrongDatesFormat() throws Exception {
        String json = """
                {
                    "start": "200-11-01",
                    "end": "2003-11-01"
                }
                """;

        mockMvc.perform(get("/users/dates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
