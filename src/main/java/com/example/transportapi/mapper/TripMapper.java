package com.example.transportapi.mapper;

import com.example.transportapi.dto.TripDTO;
import com.example.transportapi.entity.Trip;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TripMapper {

    @Mapping(source = "id", target = "tripId")
    @Mapping(source = "busPass.shift.startTime", target = "shiftStartTime")
    @Mapping(source = "busPass.shift.endTime", target = "shiftEndTime")
    @Mapping(source = "busPass.route.bus.busNumber", target = "busRegNumber")
    @Mapping(source = "busPass.pickupPoint.name", target = "pickUpPointName")
    @Mapping(source = "busPass.pickupPoint.expectedArrival", target = "pickUpTime")
    TripDTO toTripDTO(Trip trip);

    List<TripDTO> toTripDTOs(List<Trip> trips);

}
