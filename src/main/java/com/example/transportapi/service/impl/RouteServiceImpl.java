package com.example.transportapi.service.impl;

import com.example.transportapi.dto.RouteCreateDTO;
import com.example.transportapi.entity.Bus;
import com.example.transportapi.entity.OfficeLocation;
import com.example.transportapi.entity.Route;
import com.example.transportapi.exception.InvalidInputException;
import com.example.transportapi.exception.ResourceNotFoundException;
import com.example.transportapi.repository.BusRepository;
import com.example.transportapi.repository.RouteRepository;
import com.example.transportapi.service.BusService;
import com.example.transportapi.service.OfficeLocationService;
import com.example.transportapi.service.RouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
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
    public Route saveRoute(RouteCreateDTO routeCreateDTO) {
        Route route;
        if(routeCreateDTO.getId() == 0){
            route = new Route();
        } else {
            route = getRouteById(routeCreateDTO.getId());
        }

        Bus bus;
        if(routeCreateDTO.getBusId() == null){
            bus = null;
        } else {
            // check if given bus id is assigned to another route
            // For update operation: if bus is assigned to the route to update - skip throw exception
            if(busService.isBusAssignedToRouteOtherThanGivenRoute(routeCreateDTO.getBusId(), routeCreateDTO.getId())){
                throw new InvalidInputException("The provided bus is already assigned to another route. Please select another bus.");
            }
            bus = busService.getBusById(routeCreateDTO.getBusId());
        }

        OfficeLocation location = officeLocationService.getOfficeLocationById(routeCreateDTO.getOfficeLocationId());

        route.setId(routeCreateDTO.getId());
        route.setName(routeCreateDTO.getName());
        route.setShift(routeCreateDTO.getShift());
        route.setOfficeLocation(location);
        route.setBus(bus);
        return routeRepository.save(route);
    }
}
