package com.example.transportapi.IntegrationTests;

import com.example.transportapi.dto.StopCreateDTO;
import com.example.transportapi.entity.*;
import com.example.transportapi.entity.enums.City;
import com.example.transportapi.entity.enums.Shift;
import com.example.transportapi.repository.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;
import java.util.List;

import static com.example.transportapi.IntegrationTests.AuthControllerTest.getToken;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static org.junit.jupiter.api.Assertions.*;
import static util.TestUtils.getAdmin;
import static util.TestUtils.getUser;

@SpringBootTest
@AutoConfigureMockMvc
class StopControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    OfficeLocationRepository officeLocationRepository;

    @Autowired
    RouteRepository routeRepository;

    @Autowired
    StopRepository stopRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ObjectMapper objectMapper;

    OfficeLocation officeLocation1;
    OfficeLocation officeLocation2;
    Route route1;
    Route route2;
    Stop stop1;
    Stop stop2;
    User user1;
    User admin;

    @BeforeEach
    void setUp() {
        officeLocation1 = new OfficeLocation();
        officeLocation1.setCity(City.BANGALORE);
        officeLocation1.setLocation("TL-1");

        officeLocation2 = new OfficeLocation();
        officeLocation2.setCity(City.MUMBAI);
        officeLocation2.setLocation("TL-2");

        route1 = new Route();
        route1.setName("test-route");
        route1.setOfficeLocation(officeLocation1);
        route1.setShift(Shift.MORNING);

        route2 = new Route();
        route2.setName("test-route2");
        route2.setOfficeLocation(officeLocation2);
        route2.setShift(Shift.MORNING);

        stop1 = new Stop();
        stop1.setExpectedArrival(LocalTime.parse("08:00"));
        stop1.setRoute(route1);
        stop1.setName("R1-S1");
        stop1.setCity(City.MUMBAI);

        stop2 = new Stop();
        stop2.setExpectedArrival(LocalTime.parse("08:10"));
        stop2.setRoute(route1);
        stop2.setName("R1-S2");
        stop2.setCity(City.MUMBAI);

        route1.setStops(List.of(stop1, stop2));

        user1 = getUser();
        admin = getAdmin();


        userRepository.saveAll(List.of(user1, admin));
        routeRepository.save(route1);
        routeRepository.save(route2);
    }

    @AfterEach
    void tearDown() {
        routeRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void givenValidStopId_whenGetStopByID_thenReturnStop() throws Exception {
        String token = getToken("admin");
        mockMvc.perform(get("/api/stops/{id}", stop1.getId())
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("R1-S1")));
    }

    @Test
    void givenNewStop_whenCreateNewStop_thenResponse201() throws Exception {
        String token = getToken("admin");
        StopCreateDTO stop = new StopCreateDTO();
        stop.setCity(City.BANGALORE);
        stop.setName("create_route");
        stop.setRouteId(route1.getId());
        stop.setExpectedArrival(LocalTime.parse("08:30"));

        mockMvc.perform(post("/api/stops")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(stop)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("create_route")));
    }

    @Test
    void givenValidRouteId_whenGetAllStopsForRouteId_thenReturnListOfStopsForRouteId() throws Exception {
        String token = getToken("user1");
        mockMvc.perform(get("/api/routes/{id}/stops", route1.getId())
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)));
    }
}
