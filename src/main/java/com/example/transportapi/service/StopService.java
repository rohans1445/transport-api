package com.example.transportapi.service;

import com.example.transportapi.dto.StopCreateDTO;
import com.example.transportapi.entity.Stop;
import com.example.transportapi.payload.StopRequest;

import java.util.List;

public interface StopService {

    Stop getStopById(Long id);

    Stop createNewStop(StopCreateDTO stopCreateDTO);

    List<Stop> getAllStops();
}
