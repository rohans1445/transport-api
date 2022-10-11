package com.example.transportapi.repository;

import com.example.transportapi.dto.TripPassengersDTO;
import com.example.transportapi.entity.Trip;
import com.example.transportapi.entity.enums.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

    @Query("SELECT new com.example.transportapi.dto.TripPassengersDTO(buspass_user.username, buspass_stop.name) FROM Trip t " +
            "JOIN t.busPass buspass " +
            "JOIN buspass.user buspass_user " +
            "JOIN buspass.route buspass_route " +
            "JOIN buspass.pickupPoint buspass_stop " +
            "WHERE t.date=:date AND buspass.shift=:shift AND buspass_route.id=:routeId")
    List<TripPassengersDTO> getAllPassengersInATrip(LocalDate date, Shift shift, Long routeId);

}
