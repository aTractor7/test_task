package com.example.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import jakarta.transaction.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
public class UserValidationTest {

    @Autowired
    private MockMvc mockMvc;

    @Value("${validation.user.min_age}")
    private int minAge;

    @Test
    void testUserWithWrongEmailCreate() throws Exception {
        String json = """
                {
                    "email": "Wrong_Email",
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
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Error caused by field email - must be a well-formed email address;")));
    }

    @Test
    void testUserWithWrongPhoneCreate() throws Exception {
        String json = """
                {
                    "email": "adrian@email.com",
                    "firstName": "Adrian",
                    "lastName": "Newey",
                    "birthDate": "1951-11-01",
                    "phoneNumber": "abc",
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
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Error caused by field phoneNumber - This not looks like phone number.;")));
    }

    @Test
    void testUserWithFutureBirthDateCreate() throws Exception {
        String json = """
                {
                    "email": "adrian@email.com",
                    "firstName": "Adrian",
                    "lastName": "Newey",
                    "birthDate": "2333-11-01",
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
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Error caused by field birthDate - Birth date should be in past;")));
    }

    @Test
    void testUserWithWrongAgeDateCreate() throws Exception {
        String json = """
                {
                    "email": "adrian@email.com",
                    "firstName": "Adrian",
                    "lastName": "Newey",
                    "birthDate": "2023-11-01",
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
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Error caused by field birthDate - Sorry, you should be at lest " + minAge + " years old.;")));
    }
}
