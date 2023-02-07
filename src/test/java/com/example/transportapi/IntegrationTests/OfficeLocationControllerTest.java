package com.example.transportapi.IntegrationTests;

import com.example.transportapi.entity.OfficeLocation;
import com.example.transportapi.entity.User;
import com.example.transportapi.entity.enums.City;
import com.example.transportapi.repository.OfficeLocationRepository;
import com.example.transportapi.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.example.transportapi.IntegrationTests.AuthControllerTest.getToken;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static util.TestUtils.getAdmin;
import static util.TestUtils.getUser;


@SpringBootTest
@AutoConfigureMockMvc
class OfficeLocationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    OfficeLocationRepository repository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserRepository userRepository;

    OfficeLocation officeLocation;
    OfficeLocation officeLocation2;
    User admin;
    User user1;

    @BeforeEach
    void setUp() {
        officeLocation = new OfficeLocation();
        officeLocation.setCity(City.BANGALORE);
        officeLocation.setLocation("TL-1");
        officeLocation2 = new OfficeLocation();
        officeLocation2.setCity(City.MUMBAI);
        officeLocation2.setLocation("TL-2");

        user1 = getUser();
        admin = getAdmin();

        userRepository.saveAll(List.of(user1, admin));
        repository.saveAll(List.of(officeLocation, officeLocation2));
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void givenId_whenGetOLByID_thenResponse200() throws Exception {
        String token = getToken("admin");
        mockMvc.perform(get("/api/officeLocation/{id}", officeLocation.getId())
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.location", is("TL-1")));
    }

    @Test
    void givenInvalidId_whenGetOLByInvalidID_thenResponse404() throws Exception {
        String token = getToken("admin");
        mockMvc.perform(get("/api/officeLocation/100")
                        .header("Authorization", token))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenListOfOL_whenGetAllOL_thenResponse200() throws Exception {
        String token = getToken("admin");
        mockMvc.perform(get("/api/officeLocation")
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)));
    }

    @Test
    void givenOL_whenCreateOL_thenResponse201() throws Exception {
        String token = getToken("admin");

        OfficeLocation officeLocation3 = new OfficeLocation();
        officeLocation3.setCity(City.MUMBAI);
        officeLocation3.setLocation("TL-3");

        mockMvc.perform(post("/api/officeLocation")
                        .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(officeLocation3)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.location", is("TL-3")));
    }
}