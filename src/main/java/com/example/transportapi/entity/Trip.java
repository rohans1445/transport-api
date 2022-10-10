package com.example.transportapi.entity;

import com.example.transportapi.entity.enums.TripStatus;
import com.example.transportapi.entity.enums.TripType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buspass_id")
    private BusPass busPass;

    @Enumerated(EnumType.STRING)
    private TripStatus status;

    @Enumerated(EnumType.STRING)
    private TripType tripType;

    private Integer verificationToken;
    private boolean tripVerified;

}
