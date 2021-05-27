package ru.otus.library.web.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@DisplayName("WelcomeController should")
@WebMvcTest(controllers = WelcomeController.class)
class WelcomeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @DisplayName("return welcome view")
    @Test
    void shouldReturnWelcome() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("Welcome"));
    }

}