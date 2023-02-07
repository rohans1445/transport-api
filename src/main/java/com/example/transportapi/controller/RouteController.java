package com.example.transportapi.controller;

import com.example.transportapi.dto.RouteCreateDTO;
import com.example.transportapi.dto.RouteResponseDTO;
import com.example.transportapi.dto.StopResponseDTO;
import com.example.transportapi.entity.Bus;
import com.example.transportapi.entity.OfficeLocation;
import com.example.transportapi.entity.Route;
import com.example.transportapi.exception.InvalidInputException;
import com.example.transportapi.mapper.RouteMapper;
import com.example.transportapi.mapper.StopMapper;
import com.example.transportapi.service.BusService;
import com.example.transportapi.service.OfficeLocationService;
import com.example.transportapi.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RouteController {

    private final OfficeLocationService officeLocationService;
    private final RouteService routeService;
    private final BusService busService;
    private final RouteMapper mapper;
    private final StopMapper stopMapper;

    @GetMapping("/routes/{routeId}")
    public ResponseEntity<RouteResponseDTO> getRouteById(@PathVariable("routeId") Long routeId){
        Route route = routeService.getRouteById(routeId);
        RouteResponseDTO res = mapper.toRouteResponseDTO(route);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/routes")
    public ResponseEntity<RouteResponseDTO> createNewRoute(@Valid @RequestBody RouteCreateDTO routeCreateDTO){
        routeCreateDTO.setId(0L);
        Route newRoute = routeService.saveRoute(routeCreateDTO);
        RouteResponseDTO res = mapper.toRouteResponseDTO(newRoute);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PutMapping("/routes/{id}")
    public ResponseEntity<RouteResponseDTO> updateRoute(@PathVariable("id") Long id, @RequestBody RouteCreateDTO routeCreateDTO){
        routeCreateDTO.setId(id);
        Route updatedRoute = routeService.saveRoute(routeCreateDTO);
        RouteResponseDTO res = mapper.toRouteResponseDTO(updatedRoute);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/routes/{routeId}/stops")
    public ResponseEntity<List<StopResponseDTO>> getAllStopsForRoute(@PathVariable("routeId") Long routeId){
        Route route = routeService.getRouteById(routeId);
        List<StopResponseDTO> res = stopMapper.toStopResponseDTOs(route.getStops());
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
