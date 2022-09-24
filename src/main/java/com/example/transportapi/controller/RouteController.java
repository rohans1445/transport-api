package com.example.transportapi.controller;

import com.example.transportapi.dto.RouteCreateDTO;
import com.example.transportapi.dto.RouteResponseDTO;
import com.example.transportapi.entity.OfficeLocation;
import com.example.transportapi.entity.Route;
import com.example.transportapi.mapper.RouteMapper;
import com.example.transportapi.service.OfficeLocationService;
import com.example.transportapi.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RouteController {

    private final OfficeLocationService officeLocationService;
    private final RouteService routeService;
    private final RouteMapper mapper;

    @GetMapping("officeLocation/{locationId}/routes")
    public ResponseEntity<List<RouteResponseDTO>> getAllRoutesForLocation(@PathVariable("locationId") Long locationId){
        OfficeLocation location = officeLocationService.getOfficeLocationById(locationId);
        List<Route> routes = location.getRoutes();
        List<RouteResponseDTO> res = mapper.toRouteResponseDTOs(routes);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/routes/{routeId}")
    public ResponseEntity<RouteResponseDTO> getRouteById(@PathVariable("routeId") Long routeId){
        Route route = routeService.getRouteById(routeId);
        RouteResponseDTO res = mapper.toRouteResponseDTO(route);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/routes")
    public ResponseEntity<RouteCreateDTO> createNewRoute(@RequestBody RouteCreateDTO routeCreateDTO){
        Route newRoute = routeService.createNewRoute(routeCreateDTO);
        RouteCreateDTO createdRoute = RouteCreateDTO.builder()
                .name(newRoute.getName())
                .shift(newRoute.getShift())
                .officeLocationId(newRoute.getOfficeLocation().getId())
                .build();
        return new ResponseEntity<>(createdRoute, HttpStatus.CREATED);
    }

}
