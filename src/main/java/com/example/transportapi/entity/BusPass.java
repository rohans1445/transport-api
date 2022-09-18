package com.example.transportapi.entity;

import com.example.transportapi.entity.enums.BusPassStatus;
import com.example.transportapi.entity.enums.BusPassType;
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
    private OfficeLocation officeLocation;

    @Enumerated(EnumType.STRING)
    private BusPassType busPassType;

    @OneToMany
    private List<BookedDates> bookedDates;

    @Enumerated(EnumType.STRING)
    private TripType tripType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shift_id")
    private Shift shift;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id")
    private Route route;
    private Integer cost;

    private BusPassStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "user_id")
    private User user;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
