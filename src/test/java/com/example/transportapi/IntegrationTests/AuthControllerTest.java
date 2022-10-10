package com.example.transportapi.IntegrationTests;

import com.example.transportapi.dto.UserRegistrationDTO;
import com.example.transportapi.entity.Address;
import com.example.transportapi.entity.User;
import com.example.transportapi.entity.enums.City;
import com.example.transportapi.payload.LoginRequest;
import com.example.transportapi.repository.UserRepository;
import com.example.transportapi.util.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    User user;

    @BeforeEach
    void setUp() {
        user = new User();
        Address address = new Address();
        address.setCity(String.valueOf(City.MUMBAI));
        address.setHouseAddress("Test address 123");
        address.setState("TEST_STATE");
        address.setPincode("366603");
        user.setRoles("ROLE_USER");
        user.setPassword(passwordEncoder.encode("pass"));
        user.setEmail("test@email.com");
        user.setAddress(address);
        user.setMobileNumber("94948485");
        user.setUsername("testuser1");
        user.setPasses(new ArrayList<>());

        userRepository.save(user);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void givenExistingUser_whenLogin_thenReturnToken() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setUsername("testuser1");
        request.setPassword("pass");
        mockMvc.perform(post("/api/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.tokenType", is("Bearer")))
                .andExpect(jsonPath("$.accessToken").isString());
    }

    @Test
    void givenExistingUser_whenTryToAccessRestrictedResourceWithValidCreds_thenResponse200() throws Exception {
        String token = getToken(user.getUsername());

        mockMvc.perform(get("/api/user/testuser1/buspass")
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void givenExistingUser_whenTryToAccessRestrictedResourceWithInvalidCreds_thenResponse403() throws Exception {
        String token = getToken(user.getUsername());

        mockMvc.perform(get("/api/user/testuser2/buspass")
                        .header("Authorization", token))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @Test
    void givenNewUser_whenRegister_thenResponse201() throws Exception {
        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
        userRegistrationDTO.setUsername("testuser2");
        userRegistrationDTO.setPassword("pass");
        userRegistrationDTO.setEmail("test@mail.com");
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRegistrationDTO)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    public static String getToken(String username){
        Date now = new Date();
        Date expiry = new Date(now.getTime() + 3600000);
        return "Bearer " + Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, "secret")
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .compact();
    }
}