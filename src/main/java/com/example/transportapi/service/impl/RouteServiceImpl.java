package com.example.transportapi.service.impl;

import com.example.transportapi.dto.RouteCreateDTO;
import com.example.transportapi.entity.OfficeLocation;
import com.example.transportapi.entity.Route;
import com.example.transportapi.exception.ResourceNotFoundException;
import com.example.transportapi.repository.RouteRepository;
import com.example.transportapi.service.OfficeLocationService;
import com.example.transportapi.service.RouteService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;
    private final OfficeLocationService officeLocationService;

    public RouteServiceImpl(RouteRepository routeRepository, OfficeLocationService officeLocationService) {
        this.routeRepository = routeRepository;
        this.officeLocationService = officeLocationService;
    }

    @Override
    public Route getRouteById(Long id) {
        Optional<Route> routeOptional = routeRepository.findById(id);
        routeOptional.orElseThrow(() -> new ResourceNotFoundException("Cannot find route with id - " + id));
        return routeOptional.get();
    }

    @Override
    public Route createNewRoute(RouteCreateDTO routeCreateDTO) {
        Route route = new Route();
        OfficeLocation location = officeLocationService.getOfficeLocationById(routeCreateDTO.getOfficeLocationId());
        route.setName(routeCreateDTO.getName());
        route.setShift(routeCreateDTO.getShift());
        route.setOfficeLocation(location);
        return routeRepository.save(route);
    }
}
