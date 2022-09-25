package com.example.transportapi.controller;

import com.example.transportapi.mapper.OfficeLocationMapper;
import com.example.transportapi.dto.OfficeLocationCreateDTO;
import com.example.transportapi.dto.OfficeLocationResponseDTO;
import com.example.transportapi.entity.OfficeLocation;
import com.example.transportapi.service.OfficeLocationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OfficeLocationController {

    private final OfficeLocationService officeLocationService;
    private final OfficeLocationMapper mapper;

    public OfficeLocationController(OfficeLocationService officeLocationService, OfficeLocationMapper mapper) {
        this.officeLocationService = officeLocationService;
        this.mapper = mapper;
    }

    @GetMapping("/officeLocation/{id}")
    public ResponseEntity<OfficeLocationResponseDTO> getOfficeLocation(@PathVariable("id") Long id){
        OfficeLocation officeLocation = officeLocationService.getOfficeLocationById(id);
        OfficeLocationResponseDTO res = mapper.toOfficeLocationResponseDTO(officeLocation);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/officeLocation")
    public ResponseEntity<List<OfficeLocationResponseDTO>> getAllOfficeLocation(){
        List<OfficeLocation> officeLocations = officeLocationService.getAllOfficeLocations();
        List<OfficeLocationResponseDTO> res = mapper.toOfficeLocationResponseDTOs(officeLocations);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/officeLocation")
    public ResponseEntity<OfficeLocationResponseDTO> createNewOfficeLocation(@RequestBody OfficeLocationCreateDTO officeLocationCreateDTO){
        OfficeLocation officeLocation = officeLocationService.createOfficeLocation(mapper.toOfficeLocation(officeLocationCreateDTO));
        OfficeLocationResponseDTO res = mapper.toOfficeLocationResponseDTO(officeLocation);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

}
