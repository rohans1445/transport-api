package com.example.transportapi.entity;

import com.example.transportapi.entity.enums.BusPassStatus;
import com.example.transportapi.entity.enums.BusPassType;
import com.example.transportapi.entity.enums.Shift;
import com.example.transportapi.entity.enums.TripType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusPass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "office_location_id")
    private OfficeLocation officeLocation;

    @Enumerated(EnumType.STRING)
    private Shift shift;

    @Enumerated(EnumType.STRING)
    private BusPassType busPassType;

    @Enumerated(EnumType.STRING)
    @Column(name = "selected_month")
    private Month month;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "busPass", cascade = CascadeType.ALL)
    private List<Trip> trips;

    @Enumerated(EnumType.STRING)
    private TripType tripType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id")
    private Route route;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pickup_point_stop_id")
    private Stop pickupPoint;

    private Integer cost;

    @Enumerated(EnumType.STRING)
    private BusPassStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
