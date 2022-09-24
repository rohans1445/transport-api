package com.example.transportapi.dto;

import com.example.transportapi.entity.enums.City;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StopCreateDTO {

    private String name;
    private City city;
    private Long routeId;
    private LocalTime expectedArrival;

}
