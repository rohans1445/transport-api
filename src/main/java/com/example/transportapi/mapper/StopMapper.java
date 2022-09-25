package com.example.transportapi.mapper;

import com.example.transportapi.dto.StopCreateDTO;
import com.example.transportapi.dto.StopResponseDTO;
import com.example.transportapi.entity.Stop;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StopMapper {

    StopResponseDTO toStopResponseDTO(Stop stop);

    Stop toStop(StopCreateDTO stopCreateDTO);

    List<StopResponseDTO> toStopResponseDTOs(List<Stop> stops);

}
