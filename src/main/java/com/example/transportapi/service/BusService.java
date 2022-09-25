package com.example.transportapi.service;

import com.example.transportapi.dto.BusCreateDTO;
import com.example.transportapi.entity.Bus;

import java.util.List;

public interface BusService {

    Bus getBusById(Long id);

    List<Bus> getAllBusses();

    Bus saveBus(BusCreateDTO busCreateDTO);

}
