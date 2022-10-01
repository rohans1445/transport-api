package com.example.transportapi.dto;

import com.example.transportapi.entity.enums.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusPassResponseDTO {

    private Long id;
    private City city;
    private Shift shift;
    private String location;
    private BusPassStatus status;
    private TripType tripType;
    private BusPassType busPassType;
    private Integer cost;
    private String routeName;
    private String pickupPointName;
    private String expectedArrival;
    private Month month;
    private List<LocalDate> selectedDates;
    private LocalDateTime issuedOn;

}
