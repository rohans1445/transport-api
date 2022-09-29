package com.example.transportapi.mapper;

import com.example.transportapi.dto.BusCreateDTO;
import com.example.transportapi.dto.BusResponseDTO;
import com.example.transportapi.entity.Bus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BusMapper {

    @Mapping(source = "route.id", target = "routeId")
    BusResponseDTO toBusResponseDTO(Bus bus);

    List<BusResponseDTO> toBusResponseDTOs(List<Bus> buses);

}
