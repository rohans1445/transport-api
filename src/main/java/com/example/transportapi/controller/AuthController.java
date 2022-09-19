package com.example.transportapi.controller;

import com.example.transportapi.payload.LoginRequest;
import com.example.transportapi.payload.LoginResponse;
import com.example.transportapi.util.JwtUtil;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log
@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest){
        UsernamePasswordAuthenticationToken UPauth = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(UPauth);
        log.info(":::::::: Inside Auth Controller - Login request for user ["+ loginRequest.getUsername() +"] successfull");
        log.info(":::::::: name - " + authentication.getName());
        log.info(":::::::: isAuthenticated - " + authentication.isAuthenticated());
        String token = jwtUtil.generateToken(authentication);

        LoginResponse res = new LoginResponse("Bearer", token);
        return ResponseEntity.ok(res);
    }

}
