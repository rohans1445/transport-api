package com.example.transportapi.controller;

import com.example.transportapi.dto.RouteResponseDTO;
import com.example.transportapi.entity.Route;
import com.example.transportapi.mapper.OfficeLocationMapper;
import com.example.transportapi.dto.OfficeLocationDTO;
import com.example.transportapi.entity.OfficeLocation;
import com.example.transportapi.mapper.RouteMapper;
import com.example.transportapi.service.OfficeLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OfficeLocationController {

    private final OfficeLocationService officeLocationService;
    private final OfficeLocationMapper mapper;
    private final RouteMapper routeMapper;

    @GetMapping("/officeLocation/{id}")
    public ResponseEntity<OfficeLocationDTO> getOfficeLocation(@PathVariable("id") Long id){
        OfficeLocation officeLocation = officeLocationService.getOfficeLocationById(id);
        OfficeLocationDTO res = mapper.toOfficeLocationDTO(officeLocation);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/officeLocation")
    public ResponseEntity<List<OfficeLocationDTO>> getAllOfficeLocation(){
        List<OfficeLocation> officeLocations = officeLocationService.getAllOfficeLocations();
        List<OfficeLocationDTO> res = mapper.toOfficeLocationDTOs(officeLocations);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/officeLocation")
    public ResponseEntity<OfficeLocationDTO> createNewOfficeLocation(@RequestBody OfficeLocationDTO officeLocationDTO){
        OfficeLocation officeLocation = officeLocationService.createOfficeLocation(mapper.toOfficeLocation(officeLocationDTO));
        OfficeLocationDTO res = mapper.toOfficeLocationDTO(officeLocation);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping("/officeLocation/{locationId}/routes")
    public ResponseEntity<List<RouteResponseDTO>> getAllRoutesForLocation(@PathVariable("locationId") Long locationId){
        OfficeLocation location = officeLocationService.getOfficeLocationById(locationId);
        List<Route> routes = location.getRoutes();
        List<RouteResponseDTO> res = routeMapper.toRouteResponseDTOs(routes);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
