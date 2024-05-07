package com.example.controllers;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAll() throws Exception {
        mockMvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getAllByBirthDateBetween() throws Exception {
        String json = """
                {
                    "start": "1951-11-01",
                    "end": "2003-11-01"
                }
                """;

        mockMvc.perform(get("/users/dates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getOne() throws Exception {
        mockMvc.perform(get("/users/{id}", 6)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void create() throws Exception{
        String json = """
                {
                    "email": "adrian@email.com",
                    "firstName": "Adrian",
                    "lastName": "Newey",
                    "birthDate": "1951-11-01",
                    "phoneNumber": null,
                    "addressDTO": {
                        "country": "Austria",
                        "city": "Vena",
                        "street": null,
                        "house": null,
                        "postIndex": "12254",
                        "userDTO": null
                    }
                }""";

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void update() throws Exception{
        String json = """
                {
                    "email": "adrian@email.com",
                    "firstName": "Adrian",
                    "lastName": "Newey",
                    "birthDate": "1951-11-01",
                    "phoneNumber": null,
                    "addressDTO": {
                        "country": "Austria",
                        "city": "Vena",
                        "street": null,
                        "house": null,
                        "postIndex": "12254",
                        "userDTO": null
                    }
                }""";

        mockMvc.perform(put("/users/{id}", 6)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void updateAddress() throws Exception{
        String json = """
                {
                    "country": "Austria",
                    "city": "Vena",
                    "street": null,
                    "house": null,
                    "postIndex": "12254",
                    "userDTO": null
                }""";

        mockMvc.perform(patch("/users/{userId}/address", 8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }
}