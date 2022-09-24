package com.example.transportapi.dto;

import com.example.transportapi.entity.enums.City;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfficeLocationCreateDTO {
    private City city;
    private String location;
}
