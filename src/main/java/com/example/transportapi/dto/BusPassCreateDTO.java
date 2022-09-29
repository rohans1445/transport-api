package com.example.transportapi.dto;

import com.example.transportapi.entity.enums.BusPassStatus;
import com.example.transportapi.entity.enums.BusPassType;
import com.example.transportapi.entity.enums.Shift;
import com.example.transportapi.entity.enums.TripType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusPassCreateDTO {

    private Long id;
    private Long officeLocationId;
    private Shift shift;
    private BusPassType busPassType;
    private List<LocalDate> selectedDates;
    private TripType tripType;
    private Long routeId;

}
