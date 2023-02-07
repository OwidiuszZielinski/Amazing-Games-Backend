package com.example.amazinggamesbackend.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
                .andExpect(MockMvcResultMatchers.status().is(200));
        //then
    }

    @Test
    public void should_get_cart_given_badUserId() throws Exception {
        //given
        final int givenUserId = 10;
        //when
        mockMvc.perform(get("/cart/" + givenUserId))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().is(422));

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

    //    @Test
//    public void should_create_cart_from_user_badUserId() throws Exception {
//        //given
//        final int givenUserId = 10;
//        //when
//
//         mockMvc.perform(post("/cart")
//                        .content(String.valueOf(givenUserId))
//                        .contentType(MediaType.APPLICATION_JSON))
//                 .andExpect()
//
//        //then
//
//    }
    @Test
    public void should_add_to_cart() throws Exception {
        //given
        final int givenUserId = 1;
        final int givenGameId = 1;
        //when

        MvcResult mvcResult = mockMvc.perform(put("/cart/{userId}", givenUserId)
                        .content(String.valueOf(givenGameId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(202))
                .andDo(print())
                .andReturn();
        //then
        Assertions.assertNotNull(mvcResult);

    }

    @Test
    public void should_add_to_cart_badGameId() throws Exception {
        //given
        final int givenUserId = 1;
        final int givenGameId = 10;
        //when

        MvcResult mvcResult = mockMvc.perform(put("/cart/{userId}", givenUserId)
                        .content(String.valueOf(givenGameId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(422))
                .andDo(print())
                .andReturn();
        //then
        Assertions.assertNotNull(mvcResult);

    }

    @Test
    public void should_add_to_cart_badUserId() throws Exception {
        //given
        final int givenUserId = 10;
        final int givenGameId = 1;
        //when

        MvcResult mvcResult = mockMvc.perform(put("/cart/{userId}", givenUserId)
                        .content(String.valueOf(givenGameId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(422))
                .andDo(print())
                .andReturn();
        //then
        Assertions.assertNotNull(mvcResult);

    }

    @Test
    public void should_delete_item_from_cart() throws Exception {
        //given
        final int givenUserId = 1;
        final int givenGameId = 1;
        mockMvc.perform(put("/cart/{userId}", givenUserId)
                .content(String.valueOf(givenGameId))
                .contentType(MediaType.APPLICATION_JSON));

        //when
        mockMvc.perform(delete("/cart/{userId}", givenUserId)
                        .content(String.valueOf(givenGameId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200)).andReturn();

    }

    @Test
    public void should_delete_item_from_cart_badUserId() throws Exception {
        //given
        final int givenUserId = 1;
        final int givenGameId = 1;
        final int badUserId = 2;
        mockMvc.perform(put("/cart/{userId}", givenUserId)
                .content(String.valueOf(givenGameId))
                .contentType(MediaType.APPLICATION_JSON));

        //when
        mockMvc.perform(delete("/cart/{userId}", badUserId)
                        .content(String.valueOf(givenGameId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(422)).andReturn();

    }

    @Test
    public void should_delete_item_from_cart_badGameId() throws Exception {
        //given
        final int givenUserId = 1;
        final int givenGameId = 1;
        final int badGameId = 2;
        mockMvc.perform(put("/cart/{userId}", givenUserId)
                .content(String.valueOf(givenGameId))
                .contentType(MediaType.APPLICATION_JSON));

        //when
        mockMvc.perform(delete("/cart/{userId}", givenUserId)
                        .content(String.valueOf(badGameId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(422)).andReturn();

    }

    @Test
    public void should_clear_all_items_from_cart() throws Exception {
        //given
        final int givenUserId = 1;
        final int givenGameId = 1;
        final int givenGameIdTwo = 2;
        mockMvc.perform(put("/cart/{userId}", givenUserId)
                .content(String.valueOf(givenGameId))
                .contentType(MediaType.APPLICATION_JSON));
        mockMvc.perform(put("/cart/{userId}", givenUserId)
                .content(String.valueOf(givenGameIdTwo))
                .contentType(MediaType.APPLICATION_JSON));
        //when
        mockMvc.perform(put("/cart/{userId}/clean", givenUserId))
                .andExpect(status().is(200));

    }

    @Test
    public void should_clear_all_items_from_cart_badCartId() throws Exception {
        //given
        final int givenUserId = 10;
        final int givenGameId = 1;
        final int givenGameIdTwo = 2;
        mockMvc.perform(put("/cart/{userId}", givenUserId)
                .content(String.valueOf(givenGameId))
                .contentType(MediaType.APPLICATION_JSON));
        mockMvc.perform(put("/cart/{userId}", givenUserId)
                .content(String.valueOf(givenGameIdTwo))
                .contentType(MediaType.APPLICATION_JSON));
        //when
        mockMvc.perform(put("/cart/{userId}/clean", givenUserId))
                .andExpect(status().is(422));

    }

}