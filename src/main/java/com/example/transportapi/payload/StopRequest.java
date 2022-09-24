package com.example.transportapi.payload;

import com.example.transportapi.entity.Route;
import com.example.transportapi.entity.enums.City;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StopRequest {
    private City city;
    private Long routeId;
    private String name;
    private LocalTime expectedArrival;
}
