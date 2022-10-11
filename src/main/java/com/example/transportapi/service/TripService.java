package com.example.transportapi.service;

import com.example.transportapi.dto.TripPassengersDTO;
import com.example.transportapi.entity.Trip;
import com.example.transportapi.entity.User;
import com.example.transportapi.payload.TripStatusUpdateRequest;

import java.util.List;

public interface TripService {

    Trip getTripById(Long id);

    Trip updateTripStatus(Long id, TripStatusUpdateRequest updateRequest);

    boolean verifyTrip(Long id, Integer verificationToken);

    List<TripPassengersDTO> getAllUsersInATrip(Long id);
    
}
