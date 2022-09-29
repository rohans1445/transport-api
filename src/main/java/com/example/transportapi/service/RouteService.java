package com.example.transportapi.service;

import com.example.transportapi.dto.RouteCreateDTO;
import com.example.transportapi.entity.Route;

public interface RouteService {

    Route getRouteById(Long id);

    Route saveRoute(RouteCreateDTO routeCreateDTO);

}
