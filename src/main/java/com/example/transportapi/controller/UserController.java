package com.example.transportapi.controller;

import com.example.transportapi.dto.BusPassResponseDTO;
import com.example.transportapi.entity.User;
import com.example.transportapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/me")
    public ResponseEntity<User> getUserDetails(){
        User currentUser = userService.getCurrentUser();
        return new ResponseEntity<>(currentUser, HttpStatus.OK);
    }

    @GetMapping("/user/{username}/buspass")
    public ResponseEntity<List<BusPassResponseDTO>> getUsersPasses(){
        List<BusPassResponseDTO> userPasses = userService.getUserPasses();
        return new ResponseEntity<>(userPasses, HttpStatus.OK);
    }

}
