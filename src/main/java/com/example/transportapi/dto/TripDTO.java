package com.example.transportapi.dto;

import com.example.transportapi.entity.enums.TripStatus;
import com.example.transportapi.entity.enums.TripType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripDTO {

    private Long tripId;
    private LocalDate date;
    private TripStatus status;
    private TripType tripType;
    private String shiftStartTime;
    private String shiftEndTime;
    private String busRegNumber;
    private String pickUpPointName;
    private LocalTime pickUpTime;
    private Integer verificationToken;
    private boolean tripVerified;

}
