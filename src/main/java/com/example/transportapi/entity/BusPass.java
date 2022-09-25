package com.example.transportapi.entity;

import com.example.transportapi.entity.enums.BusPassStatus;
import com.example.transportapi.entity.enums.BusPassType;
import com.example.transportapi.entity.enums.Shift;
import com.example.transportapi.entity.enums.TripType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusPass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "office_location_id")
    private OfficeLocation officeLocation;

    private Shift shift;

    @Enumerated(EnumType.STRING)
    private BusPassType busPassType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "busPass")
    private List<BookedDates> bookedDates;

    @Enumerated(EnumType.STRING)
    private TripType tripType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id")
    private Route route;

    private Integer cost;

    @Enumerated(EnumType.STRING)
    private BusPassStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
