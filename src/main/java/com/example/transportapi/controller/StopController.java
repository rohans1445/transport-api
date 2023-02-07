package com.example.transportapi.controller;

import com.example.transportapi.dto.StopCreateDTO;
import com.example.transportapi.dto.StopResponseDTO;
import com.example.transportapi.entity.Route;
import com.example.transportapi.entity.Stop;
import com.example.transportapi.exception.InvalidInputException;
import com.example.transportapi.mapper.StopMapper;
import com.example.transportapi.payload.ApiResponse;
import com.example.transportapi.payload.StopRequest;
import com.example.transportapi.payload.StopResponse;
import com.example.transportapi.service.RouteService;
import com.example.transportapi.service.StopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StopController {

    private final StopService stopService;
    private final RouteService routeService;
    private final StopMapper mapper;

    @GetMapping("/stops/{id}")
    public ResponseEntity<?> getStopById(@PathVariable("id") Long id){
        Stop stop = stopService.getStopById(id);
        StopResponse res = StopResponse.builder()
                .routeId(stop.getRoute().getId())
                .city(stop.getCity())
                .name(stop.getName())
                .expectedArrival(stop.getExpectedArrival())
                .build();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/stops")
    public ResponseEntity<StopResponseDTO> createNewStop(@RequestBody StopCreateDTO stopCreateDTO){
        Stop savedStop = stopService.createNewStop(stopCreateDTO);
        StopResponseDTO res = mapper.toStopResponseDTO(savedStop);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
}
