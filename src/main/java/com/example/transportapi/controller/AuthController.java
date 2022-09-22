package com.example.transportapi.controller;

import com.example.transportapi.entity.User;
import com.example.transportapi.exception.InvalidInputException;
import com.example.transportapi.payload.LoginRequest;
import com.example.transportapi.payload.LoginResponse;
import com.example.transportapi.payload.RegisterRequest;
import com.example.transportapi.payload.RegisterResponse;
import com.example.transportapi.service.UserService;
import com.example.transportapi.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, UserService userService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest){
        UsernamePasswordAuthenticationToken UPauth = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(UPauth);
        log.debug(":::::::: Inside Auth Controller - Login request for user ["+ loginRequest.getUsername() +"]");
        log.debug(":::::::: name - " + authentication.getName());
        log.debug(":::::::: isAuthenticated - " + authentication.isAuthenticated());
        log.debug(":::::::: Authentication - {}", authentication);
        String token = jwtUtil.generateToken(authentication);

        LoginResponse res = new LoginResponse("Bearer", token);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody RegisterRequest registerRequest){
        if(userService.ifUsernameExists(registerRequest.getUsername())){
            throw new InvalidInputException("An account exists with that username.");
        }
        if(userService.ifEmailExists(registerRequest.getEmail())){
            throw new InvalidInputException("An account exists with that email.");
        }
        User user = new User(registerRequest);
        User savedUser = userService.saveUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

}
