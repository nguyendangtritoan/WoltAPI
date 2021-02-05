package com.example.restapi.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class DiscoveryControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void greeting() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/");
        MvcResult result = this.mvc.perform(requestBuilder).andReturn();
        assertNotNull(result.getResponse());
    } // Unit test for get request to "/"


    @Test
    public void getSectionsResponse() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/discovery?lat=60.1709&lon=24.903244");
        MvcResult result = this.mvc.perform(requestBuilder).andReturn();
        assertNotNull(result.getResponse());
    } // Unit test for get request to discovery with parameter
}