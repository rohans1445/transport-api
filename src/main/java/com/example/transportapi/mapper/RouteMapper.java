package com.example.transportapi.mapper;

import com.example.transportapi.dto.RouteCreateDTO;
import com.example.transportapi.dto.RouteResponseDTO;
import com.example.transportapi.entity.Route;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RouteMapper {

    @Mapping(source = "officeLocation.city", target = "city")
    @Mapping(source = "officeLocation.location", target = "officeLocation")
    RouteResponseDTO toRouteResponseDTO(Route route);

    Route toRoute(RouteCreateDTO routeCreateDTO);

    List<RouteResponseDTO> toRouteResponseDTOs(List<Route> routes);

}
