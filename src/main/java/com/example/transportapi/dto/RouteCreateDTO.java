package com.example.transportapi.dto;

import com.example.transportapi.entity.enums.Shift;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RouteCreateDTO {

    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private Shift shift;

    @NotNull(message = "Office Location id must not be null.")
    private Long officeLocationId;

    private Long busId;

}
