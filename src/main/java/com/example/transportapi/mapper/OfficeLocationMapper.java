package com.example.transportapi.mapper;

import com.example.transportapi.dto.OfficeLocationDTO;
import com.example.transportapi.entity.OfficeLocation;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OfficeLocationMapper {

    OfficeLocationDTO toOfficeLocationDTO(OfficeLocation officeLocation);

    OfficeLocation toOfficeLocation(OfficeLocationDTO officeLocationDTO);

    List<OfficeLocationDTO> toOfficeLocationDTOs(List<OfficeLocation> officeLocations);
}
