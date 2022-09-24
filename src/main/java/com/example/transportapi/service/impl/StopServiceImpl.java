package com.example.transportapi.service.impl;

import com.example.transportapi.dto.StopCreateDTO;
import com.example.transportapi.entity.Route;
import com.example.transportapi.entity.Stop;
import com.example.transportapi.exception.ResourceNotFoundException;
import com.example.transportapi.repository.StopRepository;
import com.example.transportapi.service.RouteService;
import com.example.transportapi.service.StopService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StopServiceImpl implements StopService {

    private final StopRepository stopRepository;
    private final RouteService routeService;

    public StopServiceImpl(StopRepository stopRepository, RouteService routeService) {
        this.stopRepository = stopRepository;
        this.routeService = routeService;
    }

    @Override
    public Stop getStopById(Long id) {
        Optional<Stop> stopOptional = stopRepository.findById(id);
        stopOptional.orElseThrow(() -> new ResourceNotFoundException("Couldn't find stop with id - " + id));
        return stopOptional.get();
    }

    @Override
    public Stop createNewStop(StopCreateDTO stopCreateDTO) {
        Route route = routeService.getRouteById(stopCreateDTO.getRouteId());
        Stop stop = new Stop();
        stop.setName(stopCreateDTO.getName());
        stop.setRoute(route);
        stop.setCity(stopCreateDTO.getCity());
        stop.setExpectedArrival(stopCreateDTO.getExpectedArrival());
        return stopRepository.save(stop);
    }

    @Override
    public List<Stop> getAllStops() {
        return stopRepository.findAll();
    }
}
