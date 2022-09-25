package com.example.transportapi.dto;

import com.example.transportapi.entity.enums.City;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OfficeLocationResponseDTO {
    private Long id;
    private City city;
    private String location;
    private LocalDateTime createdAt;
}
