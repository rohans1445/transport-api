package com.example.transportapi.service.impl;

import com.example.transportapi.dto.RouteCreateDTO;
import com.example.transportapi.entity.Bus;
import com.example.transportapi.entity.OfficeLocation;
import com.example.transportapi.entity.Route;
import com.example.transportapi.exception.ResourceNotFoundException;
import com.example.transportapi.repository.BusRepository;
import com.example.transportapi.repository.RouteRepository;
import com.example.transportapi.service.BusService;
import com.example.transportapi.service.OfficeLocationService;
import com.example.transportapi.service.RouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;
    private final OfficeLocationService officeLocationService;
    private final BusService busService;

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

    @Override
    public Route updateRoute(RouteCreateDTO routeCreateDTO, Route routeToUpdate) {
        OfficeLocation officeLocation = officeLocationService.getOfficeLocationById(routeCreateDTO.getOfficeLocationId());
        Bus bus = busService.getBusById(routeCreateDTO.getBusId());

        routeToUpdate.setName(routeCreateDTO.getName());
        routeToUpdate.setShift(routeCreateDTO.getShift());
        routeToUpdate.setOfficeLocation(officeLocation);
        routeToUpdate.setBus(bus);
        log.info("Updating route - {}", routeToUpdate);
        return routeRepository.save(routeToUpdate);
    }
}
