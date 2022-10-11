package com.example.transportapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripPassengersDTO {
    private String username;
    private String pickupPoint;
}
