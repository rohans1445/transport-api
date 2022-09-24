package com.example.transportapi.mapper;

import com.example.transportapi.dto.OfficeLocationCreateDTO;
import com.example.transportapi.dto.OfficeLocationResponseDTO;
import com.example.transportapi.entity.OfficeLocation;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OfficeLocationMapper {

    OfficeLocationResponseDTO toOfficeLocationResponseDTO(OfficeLocation officeLocation);

    OfficeLocationCreateDTO toOfficeLocationCreateDTO(OfficeLocation officeLocation);

    OfficeLocation toOfficeLocation(OfficeLocationCreateDTO officeLocationCreateDTO);

    OfficeLocation toOfficeLocation(OfficeLocationResponseDTO officeLocationResponseDTO);

    List<OfficeLocationResponseDTO> toOfficeLocationResponseDTOs(List<OfficeLocation> officeLocations);
}
