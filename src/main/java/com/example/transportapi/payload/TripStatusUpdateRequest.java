package com.example.transportapi.payload;

import com.example.transportapi.entity.enums.TripStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripStatusUpdateRequest {
    private TripStatus status;
}
