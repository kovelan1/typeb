package com.typeb.webapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for GET /hello-world?name={name}.
 *
 * Rules:
 *   - name starts with A–M (case-insensitive) → 200 { "message": "Hello <Capitalised>" }
 *   - name starts with N–Z (case-insensitive) → 400 { "error": "Invalid Input" }
 *   - name missing or empty                   → 400 { "error": "Invalid Input" }
 */
@SpringBootTest
class HelloWorldControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    // -------------------------------------------------------------------------
    // Happy-path: first letter A–M
    // -------------------------------------------------------------------------

    @Test
    void givenNameStartingWithA_whenGetHelloWorld_thenReturns200WithMessage() throws Exception {
        mockMvc.perform(get("/hello-world").param("name", "alice"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello Alice"));
    }

    @Test
    void givenNameStartingWithUppercaseA_whenGetHelloWorld_thenReturns200WithMessage() throws Exception {
        mockMvc.perform(get("/hello-world").param("name", "Alice"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello Alice"));
    }

    @Test
    void givenNameStartingWithM_boundaryLow_whenGetHelloWorld_thenReturns200WithMessage() throws Exception {
        mockMvc.perform(get("/hello-world").param("name", "mike"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello Mike"));
    }

    @Test
    void givenNameStartingWithUppercaseM_boundaryLow_whenGetHelloWorld_thenReturns200WithMessage() throws Exception {
        mockMvc.perform(get("/hello-world").param("name", "Max"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello Max"));
    }

    @Test
    void givenSingleLetterA_whenGetHelloWorld_thenReturns200WithMessage() throws Exception {
        mockMvc.perform(get("/hello-world").param("name", "a"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello A"));
    }

    @Test
    void givenSingleLetterM_whenGetHelloWorld_thenReturns200WithMessage() throws Exception {
        mockMvc.perform(get("/hello-world").param("name", "m"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello M"));
    }

    // -------------------------------------------------------------------------
    // Error-path: first letter N–Z
    // -------------------------------------------------------------------------

    @Test
    void givenNameStartingWithN_boundaryHigh_whenGetHelloWorld_thenReturns400WithError() throws Exception {
        mockMvc.perform(get("/hello-world").param("name", "nick"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid Input"));
    }

    @Test
    void givenNameStartingWithUppercaseN_whenGetHelloWorld_thenReturns400WithError() throws Exception {
        mockMvc.perform(get("/hello-world").param("name", "Nancy"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid Input"));
    }

    @Test
    void givenNameStartingWithZ_whenGetHelloWorld_thenReturns400WithError() throws Exception {
        mockMvc.perform(get("/hello-world").param("name", "zara"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid Input"));
    }

    @Test
    void givenSingleLetterN_whenGetHelloWorld_thenReturns400WithError() throws Exception {
        mockMvc.perform(get("/hello-world").param("name", "n"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid Input"));
    }

    @Test
    void givenSingleLetterZ_whenGetHelloWorld_thenReturns400WithError() throws Exception {
        mockMvc.perform(get("/hello-world").param("name", "z"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid Input"));
    }

    // -------------------------------------------------------------------------
    // Error-path: missing or empty name
    // -------------------------------------------------------------------------

    @Test
    void givenMissingNameParam_whenGetHelloWorld_thenReturns400WithError() throws Exception {
        mockMvc.perform(get("/hello-world"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid Input"));
    }

    @Test
    void givenEmptyNameParam_whenGetHelloWorld_thenReturns400WithError() throws Exception {
        mockMvc.perform(get("/hello-world").param("name", ""))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid Input"));
    }

    @Test
    void givenBlankNameParam_whenGetHelloWorld_thenReturns400WithError() throws Exception {
        mockMvc.perform(get("/hello-world").param("name", "   "))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid Input"));
    }

    // -------------------------------------------------------------------------
    // Edge cases: non-alpha first character
    // -------------------------------------------------------------------------

    @Test
    void givenNameStartingWithDigit_whenGetHelloWorld_thenReturns400WithError() throws Exception {
        mockMvc.perform(get("/hello-world").param("name", "123abc"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid Input"));
    }

    @Test
    void givenNameStartingWithSpecialChar_whenGetHelloWorld_thenReturns400WithError() throws Exception {
        mockMvc.perform(get("/hello-world").param("name", "@alice"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid Input"));
    }
}
