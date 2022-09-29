package com.example.transportapi.controller;

import com.example.transportapi.dto.BusCreateDTO;
import com.example.transportapi.dto.BusResponseDTO;
import com.example.transportapi.entity.Bus;
import com.example.transportapi.mapper.BusMapper;
import com.example.transportapi.service.BusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BusController {

    private final BusService busService;
    private final BusMapper mapper;

    @GetMapping("/bus/{id}")
    public ResponseEntity<BusResponseDTO> getBusById(@PathVariable("id") Long id){
        Bus bus = busService.getBusById(id);
        BusResponseDTO res = mapper.toBusResponseDTO(bus);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/bus")
    public ResponseEntity<List<BusResponseDTO>> getAllBusses(){
        List<Bus> busses = busService.getAllBusses();
        List<BusResponseDTO> res = mapper.toBusResponseDTOs(busses);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/bus")
    public ResponseEntity<BusResponseDTO> addNewBus(@RequestBody BusCreateDTO busCreateDTO){
        Bus savedBus = busService.saveBus(busCreateDTO);
        BusResponseDTO res = mapper.toBusResponseDTO(savedBus);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping("/bus/available")
    public ResponseEntity<List<BusResponseDTO>> getAvailableBusses(){
        List<Bus> busList = busService.getAvailableBusses();
        List<BusResponseDTO> res = mapper.toBusResponseDTOs(busList);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
