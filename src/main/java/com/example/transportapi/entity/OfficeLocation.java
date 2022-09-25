package com.example.transportapi.entity;

import com.example.transportapi.entity.enums.City;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfficeLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private City city;

    private String location;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "officeLocation")
    private List<Route> routes;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
