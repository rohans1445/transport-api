package com.example.transportapi.controller;

import com.example.transportapi.dto.BusPassResponseDTO;
import com.example.transportapi.dto.CurrentUserDTO;
import com.example.transportapi.entity.User;
import com.example.transportapi.mapper.BusPassMapper;
import com.example.transportapi.service.BusPassService;
import com.example.transportapi.service.TripService;
import com.example.transportapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final TripService tripService;
    private final BusPassService busPassService;
    private final BusPassMapper busPassMapper;

    @GetMapping("/user/me")
    public ResponseEntity<CurrentUserDTO> getUserDetails(){
        CurrentUserDTO currentUser = new CurrentUserDTO(userService.getCurrentUser());
        return new ResponseEntity<>(currentUser, HttpStatus.OK);
    }

    @GetMapping("/user/{username}/buspass")
    @PreAuthorize("hasRole('ADMIN') or authentication.name == #username")
    public ResponseEntity<List<BusPassResponseDTO>> getUsersPasses(@PathVariable("username") String username){
        List<BusPassResponseDTO> userPasses = userService.getUserPasses(username);
        return new ResponseEntity<>(userPasses, HttpStatus.OK);
    }

//    @GetMapping("/user/{username}/buspass/current")
//    public ResponseEntity<?> getCurrentActivePassForUser(@PathVariable("username") String username){
//        BusPass currentPass;
//        try {
//            currentPass = busPassService.getCurrentActiveBusPassForUser(username);
//        } catch (RuntimeException e){
//            return new ResponseEntity<>(ApiResponse.builder()
//                    .status(HttpStatus.NOT_FOUND.value())
//                    .message("No active pass found for given user").build(), HttpStatus.NOT_FOUND);
//        }
//        BusPassResponseDTO res = busPassMapper.toBusPassResponseDTO(currentPass);
//        return new ResponseEntity<>(res, HttpStatus.OK);
//    }

}
