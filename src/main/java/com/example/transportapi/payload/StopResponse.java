package com.example.transportapi.payload;

import com.example.transportapi.entity.enums.City;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StopResponse {
    private City city;
    private String name;
    private Long routeId;
    private LocalTime expectedArrival;
}
