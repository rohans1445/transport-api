package com.example.transportapi.controller;

import com.example.transportapi.dto.BusPassCreateDTO;
import com.example.transportapi.dto.BusPassResponseDTO;
import com.example.transportapi.dto.TripDTO;
import com.example.transportapi.entity.BusPass;
import com.example.transportapi.mapper.BusPassMapper;
import com.example.transportapi.mapper.TripMapper;
import com.example.transportapi.service.BusPassService;
import com.example.transportapi.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BusPassController {

    private final BusPassService busPassService;
    private final BusPassMapper passMapper;
    private final TripMapper tripMapper;
    private final TripService tripService;

    @PostMapping("/buspass")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BusPassResponseDTO> createBusPass(@RequestBody BusPassCreateDTO busPassCreateDTO){
        BusPass savedBusPass = busPassService.saveBusPass(busPassCreateDTO);
        BusPassResponseDTO res = passMapper.toBusPassResponseDTO(savedBusPass);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping("/buspass/{id}")
    public ResponseEntity<BusPassResponseDTO> getBusPassById(@PathVariable("id") Long id){
        BusPass busPass = busPassService.getBusPassById(id);
        BusPassResponseDTO res = passMapper.toBusPassResponseDTO(busPass);
        return new ResponseEntity<>(res, OK);
    }

    @GetMapping("/buspass/{id}/trips")
    public ResponseEntity<List<TripDTO>> getTripsForAPass(@PathVariable("id") Long id){
        BusPass pass = busPassService.getBusPassById(id);
        List<TripDTO> tripDTOS = tripMapper.toTripDTOs(pass.getTrips());
        return new ResponseEntity<>(tripDTOS, OK);
    }

}
