package com.example.transportapi.dto;

import com.example.transportapi.entity.Bus;
import com.example.transportapi.entity.enums.Shift;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteResponseDTO {
    private Long id;
    private String name;
    private Bus bus;
    private Shift shift;
    private LocalDateTime createdAt;
}
