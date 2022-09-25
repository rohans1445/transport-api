package com.example.transportapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusResponseDTO {

    private Long id;
    private String busNumber;
    private Integer seatingCapacity;

}
