package com.example.transportapi.dto;

import com.example.transportapi.entity.enums.Shift;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RouteCreateDTO {
    private String name;
    private Shift shift;
    private Long officeLocationId;
    private Long busId;
}
