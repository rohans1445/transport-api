package com.example.transportapi.IntegrationTests;

import com.example.transportapi.dto.BusPassCreateDTO;
import com.example.transportapi.entity.*;
import com.example.transportapi.entity.enums.BusPassType;
import com.example.transportapi.entity.enums.City;
import com.example.transportapi.mapper.BusPassMapper;
import com.example.transportapi.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static com.example.transportapi.IntegrationTests.AuthControllerTest.getToken;
import static com.example.transportapi.entity.enums.BusPassType.*;
import static com.example.transportapi.entity.enums.Shift.MORNING;
import static com.example.transportapi.entity.enums.TripType.BOTH;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BusPassControllerTest {

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private BusPassRepository busPassRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private BusPassMapper busPassMapper;

    OfficeLocation officeLocation1;
    OfficeLocation officeLocation2;
    Bus bus1;
    Bus bus2;
    Route route1;
    Route route2;
    Stop stop1;
    Stop stop2;
    BusPass pass1;
    User user1;

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
        route1.setShift(MORNING);
        route1.setBus(bus1);

        route2 = new Route();
        route2.setName("test-route2");
        route2.setOfficeLocation(officeLocation2);
        route2.setShift(MORNING);

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

        user1 = new User();
        Address address = new Address();
        address.setCity(String.valueOf(City.MUMBAI));
        address.setHouseAddress("Test address 123");
        address.setState("TEST_STATE");
        address.setPincode("366603");
        user1.setRoles("ROLE_USER");
        user1.setPassword(passwordEncoder.encode("pass"));
        user1.setEmail("test@email.com");
        user1.setAddress(address);
        user1.setMobileNumber("94948485");
        user1.setUsername("testuser1");

        userRepository.save(user1);
        routeRepository.save(route1);
        routeRepository.save(route2);
        busRepository.save(bus2);
    }

    @AfterEach
    void tearDown() {
        tripRepository.deleteAll();
        busPassRepository.deleteAll();
        userRepository.deleteAll();
        routeRepository.deleteAll();
        busRepository.deleteAll();
    }

    @Test
    void givenUser_whenCreatePassOfTypeSingle_thenResponse201() throws Exception {
        String token = getToken("testuser1");
        BusPassCreateDTO busPass = createBusPassRequest(SINGLE);

        mockMvc.perform(post("/api/buspass")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(busPass))
                    .header("Authorization", token))
                .andExpect(status().isCreated())
                .andDo(print());

    }

    @Test
    void givenUser_whenCreatePassOfTypeHybrid_thenResponse201() throws Exception {
        String token = getToken("testuser1");
        BusPassCreateDTO busPass = createBusPassRequest(HYBRID);

        mockMvc.perform(post("/api/buspass")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(busPass))
                    .header("Authorization", token))
                .andExpect(status().isCreated())
                .andDo(print());

    }

    @Test
    void givenUser_whenCreatePassOfTypeMonthly_thenResponse201() throws Exception {
        String token = getToken("testuser1");
        BusPassCreateDTO busPass = createBusPassRequest(MONTHLY);

        mockMvc.perform(post("/api/buspass")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(busPass))
                    .header("Authorization", token))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.tripType", is("BOTH")))
                .andDo(print());

    }


    @Test
    void getBusPassById() {
    }

    @Test
    void getTripsForAPass() {
    }

    @Test
    void cancelTrip() {
    }

    private BusPassCreateDTO createBusPassRequest(BusPassType type) {
        switch(type){
            case SINGLE:
                return BusPassCreateDTO.builder()
                    .busPassType(type)
                    .shift(MORNING)
                    .month(LocalDate.now().getMonth())
                    .officeLocationId(officeLocation1.getId())
                    .routeId(route1.getId())
                    .tripType(BOTH)
                    .pickupPointId(stop1.getId())
                    .selectedDates(List.of(LocalDate.now().plusDays(2)))
                    .build();
            case MONTHLY:
                return BusPassCreateDTO.builder()
                    .busPassType(type)
                    .shift(MORNING)
                    .month(LocalDate.now().getMonth().plus(1))
                    .officeLocationId(officeLocation1.getId())
                    .routeId(route1.getId())
                    .tripType(BOTH)
                    .pickupPointId(stop1.getId())
                    .build();
            case HYBRID:
                return BusPassCreateDTO.builder()
                    .busPassType(type)
                    .shift(MORNING)
                    .month(LocalDate.now().getMonth().plus(1))
                    .officeLocationId(officeLocation1.getId())
                    .routeId(route1.getId())
                    .tripType(BOTH)
                    .pickupPointId(stop1.getId())
                    .selectedDates(List.of(
                            LocalDate.now().plusMonths(1).plusDays(2),
                            LocalDate.now().plusMonths(1).plusDays(4),
                            LocalDate.now().plusMonths(1).plusDays(6),
                            LocalDate.now().plusMonths(1).plusDays(8)))
                    .build();
        }
        return null;
    }

}
