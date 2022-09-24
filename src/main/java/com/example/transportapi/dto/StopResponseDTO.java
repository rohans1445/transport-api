package com.example.transportapi.dto;

import com.example.transportapi.entity.enums.City;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StopResponseDTO {

    private Long id;
    private String name;
    private LocalTime expectedArrival;
    private City city;

}
