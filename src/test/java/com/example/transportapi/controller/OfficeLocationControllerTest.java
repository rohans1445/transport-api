package com.example.transportapi.controller;

import com.example.transportapi.entity.OfficeLocation;
import com.example.transportapi.entity.enums.City;
import com.example.transportapi.repository.OfficeLocationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class OfficeLocationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    OfficeLocationRepository repository;

    @Autowired
    ObjectMapper objectMapper;

    OfficeLocation officeLocation;

    OfficeLocation officeLocation2;

    @BeforeEach
    void setUp() {
        officeLocation = new OfficeLocation();
        officeLocation.setCity(City.BANGALORE);
        officeLocation.setLocation("TL-1");
        officeLocation2 = new OfficeLocation();
        officeLocation2.setCity(City.MUMBAI);
        officeLocation2.setLocation("TL-2");
        repository.saveAll(List.of(officeLocation, officeLocation2));
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void givenId_whenGetOLByID_thenResponse200() throws Exception {
        mockMvc.perform(get("/api/officeLocation/{id}", officeLocation.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.location", is("TL-1")));
    }

    @Test
    void givenInvalidId_whenGetOLByInvalidID_thenResponse404() throws Exception {
        mockMvc.perform(get("/api/officeLocation/100"))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenListOfOL_whenGetAllOL_thenResponse200() throws Exception {
        mockMvc.perform(get("/api/officeLocation"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)));
    }

    @Test
    void givenOL_whenCreateOL_thenResponse201() throws Exception {

        OfficeLocation officeLocation3 = new OfficeLocation();
        officeLocation3.setCity(City.MUMBAI);
        officeLocation3.setLocation("TL-3");

        mockMvc.perform(post("/api/officeLocation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(officeLocation3)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.location", is("TL-3")));
    }
}