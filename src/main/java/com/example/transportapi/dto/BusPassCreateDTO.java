package com.example.transportapi.dto;

import com.example.transportapi.entity.enums.BusPassStatus;
import com.example.transportapi.entity.enums.BusPassType;
import com.example.transportapi.entity.enums.Shift;
import com.example.transportapi.entity.enums.TripType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusPassCreateDTO {

    private Long id;

    @NotNull(message = "Office location id cannot be null")
    private Long officeLocationId;

    @NotNull(message = "Shift cannot be null")
    private Shift shift;

    @NotNull(message = "Bus pass type cannot be null")
    private BusPassType busPassType;

    private List<LocalDate> selectedDates;

    @NotNull(message = "Trip type cannot be null")
    private TripType tripType;

    @NotNull(message = "Route id cannot be null")
    private Long routeId;

}
