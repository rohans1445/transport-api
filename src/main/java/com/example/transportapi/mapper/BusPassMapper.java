package com.example.transportapi.mapper;

import com.example.transportapi.dto.BusPassResponseDTO;
import com.example.transportapi.entity.BusPass;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BusPassMapper {

    default BusPassResponseDTO toBusPassResponseDTO(BusPass busPass) {
        return BusPassResponseDTO.builder()
                .id(busPass.getId())
                .busPassType(busPass.getBusPassType())
                .shift(busPass.getShift())
                .city(busPass.getOfficeLocation().getCity())
                .location(busPass.getOfficeLocation().getLocation())
                .status(busPass.getStatus())
                .tripType(busPass.getTripType())
                .issuedOn(busPass.getCreatedAt())
                .routeName(busPass.getRoute().getName())
                .cost(busPass.getCost())
                .selectedDates(busPass.getBookedDates()
                        .stream()
                        .map(bookedDate -> LocalDate.parse(bookedDate.getDate().toString()))
                        .collect(Collectors.toList()))
                .build();
    }

    List<BusPassResponseDTO> toBusPassResponseDTOs(List<BusPass> busPasses);

}
