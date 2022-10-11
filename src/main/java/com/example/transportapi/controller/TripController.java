package com.example.transportapi.controller;


import com.example.transportapi.dto.TripDTO;
import com.example.transportapi.dto.TripPassengersDTO;
import com.example.transportapi.entity.Trip;
import com.example.transportapi.entity.User;
import com.example.transportapi.mapper.TripMapper;
import com.example.transportapi.payload.ApiResponse;
import com.example.transportapi.payload.TripStatusUpdateRequest;
import com.example.transportapi.payload.TripVerifyRequest;
import com.example.transportapi.service.BusPassService;
import com.example.transportapi.service.TripService;
import com.example.transportapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TripController {

    private final BusPassService busPassService;
    private final TripService tripService;
    private final UserService userService;
    private final TripMapper tripMapper;

    @GetMapping("/trips/{id}")
    public ResponseEntity<TripDTO> getTripById(@PathVariable("id") Long id){
        Trip trip = tripService.getTripById(id);
        TripDTO tripDTO = tripMapper.toTripDTO(trip);
        return ResponseEntity.ok(tripDTO);
    }

    @PatchMapping("/trips/{id}/status")
    public ResponseEntity<TripDTO> updateTripStatus(@RequestBody TripStatusUpdateRequest updateRequest, @PathVariable("id") Long id){
        Trip updatedTrip = tripService.updateTripStatus(id, updateRequest);
        TripDTO res = tripMapper.toTripDTO(updatedTrip);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/trips/{id}/verify")
    public ResponseEntity<ApiResponse> verifyTrip(@RequestBody TripVerifyRequest tripVerifyRequest, @PathVariable("id") Long id){
        boolean verified = tripService.verifyTrip(id, tripVerifyRequest.getVerificationToken());
        if(verified){
            return new ResponseEntity<>(ApiResponse.builder()
                    .message("Trip verified successfully.")
                    .status(HttpStatus.OK.value()).build(), HttpStatus.OK);
        }
        return new ResponseEntity<>(ApiResponse.builder()
                    .message("Invalid trip verification token.")
                    .status(HttpStatus.BAD_REQUEST.value()).build(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/trips/{id}/users")
    public ResponseEntity<List<TripPassengersDTO>> getAllUsersForATrip(@PathVariable("id") Long id){
        List<TripPassengersDTO> passengers = tripService.getAllUsersInATrip(id);
        return new ResponseEntity<>(passengers, HttpStatus.OK);
    }
}
