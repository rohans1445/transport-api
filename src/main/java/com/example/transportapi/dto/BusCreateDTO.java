package com.example.transportapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusCreateDTO {

    private String busNumber;
    private Integer seatingCapacity;

}
