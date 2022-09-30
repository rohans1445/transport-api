package com.example.transportapi.controller;

import com.example.transportapi.dto.BusPassCreateDTO;
import com.example.transportapi.dto.BusPassResponseDTO;
import com.example.transportapi.entity.BusPass;
import com.example.transportapi.mapper.BusPassMapper;
import com.example.transportapi.service.BusPassService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BusPassController {

    private final BusPassService busPassService;
    private final BusPassMapper mapper;

    @PostMapping("/buspass")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BusPassResponseDTO> createBusPass(@RequestBody BusPassCreateDTO busPassCreateDTO){
        BusPass savedBusPass = busPassService.saveBusPass(busPassCreateDTO);
        BusPassResponseDTO res = mapper.toBusPassResponseDTO(savedBusPass);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping("/buspass/{id}")
    public ResponseEntity<BusPassResponseDTO> getBusPassById(@PathVariable("id") Long id){
        BusPass busPass = busPassService.getBusPassById(id);
        BusPassResponseDTO res = mapper.toBusPassResponseDTO(busPass);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
