package com.example.amazinggamesbackend.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
//@WithMockUser
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void should_get_cart_given_user() throws Exception {
        //given
        final int givenUserId = 1;
        //when
        mockMvc.perform(get("/cart/" + givenUserId))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(jsonPath("$.id" ,Matchers.is(givenUserId)));
        //then
    }

    @Test
    public void should_create_cart_from_exist_user() throws Exception {
        //given
        final int givenUserId = 1;
        //when
        MvcResult mvcResult = mockMvc.perform(post("/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(givenUserId)))
                .andDo(print())
                .andExpect(status().is(422))
                .andReturn();

    }

    @Test
    public void should_create_cart_from_user() throws Exception {
        //given
        final int givenUserId = 5;
        //when

        MvcResult mvcResult = mockMvc.perform(post("/cart")
                        .content(String.valueOf(givenUserId))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().is(201))
                        .andReturn();
        //then

    }

}