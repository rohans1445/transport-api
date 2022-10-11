package com.example.transportapi.IntegrationTests;

import com.example.transportapi.dto.RouteCreateDTO;
import com.example.transportapi.entity.Bus;
import com.example.transportapi.entity.OfficeLocation;
import com.example.transportapi.entity.Route;
import com.example.transportapi.entity.enums.City;
import com.example.transportapi.entity.enums.Shift;
import com.example.transportapi.repository.BusRepository;
import com.example.transportapi.repository.OfficeLocationRepository;
import com.example.transportapi.repository.RouteRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RouteControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    OfficeLocationRepository officeLocationRepository;

    @Autowired
    RouteRepository routeRepository;

    @Autowired
    BusRepository busRepository;

    @Autowired
    ObjectMapper objectMapper;

    OfficeLocation officeLocation1;
    OfficeLocation officeLocation2;
    Bus bus1;
    Bus bus2;
    Route route1;
    Route route2;

    @BeforeEach
    void setUp() {
        officeLocation1 = new OfficeLocation();
        officeLocation1.setCity(City.BANGALORE);
        officeLocation1.setLocation("TL-1");

        officeLocation2 = new OfficeLocation();
        officeLocation2.setCity(City.MUMBAI);
        officeLocation2.setLocation("TL-2");

        bus1 = new Bus();
        bus1.setBusNumber("A3434");
        bus1.setSeatingCapacity(25);
        bus2 = new Bus();
        bus2.setBusNumber("B3434");
        bus2.setSeatingCapacity(25);


        route1 = new Route();
        route1.setName("test-route");
        route1.setOfficeLocation(officeLocation1);
        route1.setShift(Shift.MORNING);
        route1.setBus(bus1);

        route2 = new Route();
        route2.setName("test-route2");
        route2.setOfficeLocation(officeLocation2);
        route2.setShift(Shift.MORNING);

        routeRepository.save(route1);
        routeRepository.save(route2);
        busRepository.save(bus2);
    }

    @AfterEach
    void tearDown() {
        routeRepository.deleteAll();
        busRepository.deleteAll();
    }

    @Test
    void givenOfficeLocationID_whenGetAllRoutesForLocation_thenReturnAllRoutes() throws Exception {
        mockMvc.perform(get("/api/officeLocation/{id}/routes", officeLocation1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].officeLocation", is("TL-1")));
    }

    @Test
    void givenRouteId_whenGetRouteByID_thenReturnRoute() throws Exception {
        mockMvc.perform(get("/api/routes/{id}", route1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("test-route")));
    }

    @Test
    void givenNewRoute_whenCreateNewRoute_thenReturnCreatedRoute() throws Exception {

        RouteCreateDTO route = RouteCreateDTO.builder()
                .name("test-create-route")
                .shift(Shift.MORNING)
                .officeLocationId(officeLocation1.getId())
                .build();

        mockMvc.perform(post("/api/routes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(route)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("test-create-route")));

    }

    @Test
    void givenExistingRoute_whenUpdateRoute_thenReturnUpdatedRoute() throws Exception {
        RouteCreateDTO route = RouteCreateDTO.builder()
                .name("test-update-route")
                .shift(Shift.AFTERNOON)
                .officeLocationId(officeLocation2.getId())
                .busId(bus2.getId())
                .build();

        // update route2
        mockMvc.perform(put("/api/routes/{id}", route2.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(route)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("test-update-route")))
                .andExpect(jsonPath("$.shift", is("AFTERNOON")))
                .andExpect(jsonPath("$.officeLocation", is("TL-2")))
                .andExpect(jsonPath("$.bus.busNumber", is("B3434")));

    }

    @Test
    void givenExistingRoute_whenUpdateRouteBusDetailsWithBusAlreadyAssigned_thenResponse400() throws Exception {
        RouteCreateDTO route = RouteCreateDTO.builder()
                .name("test-update-route")
                .shift(Shift.AFTERNOON)
                .officeLocationId(officeLocation2.getId())
                .busId(bus1.getId())
                .build();

        mockMvc.perform(put("/api/routes/{id}", route2.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(route)))
                .andExpect(status().isBadRequest());
    }
}
