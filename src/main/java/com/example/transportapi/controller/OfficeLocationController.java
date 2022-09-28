package com.example.transportapi.controller;

import com.example.transportapi.mapper.OfficeLocationMapper;
import com.example.transportapi.dto.OfficeLocationCreateDTO;
import com.example.transportapi.dto.OfficeLocationDTO;
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

}
