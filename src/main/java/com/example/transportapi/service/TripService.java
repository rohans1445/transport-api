package com.example.transportapi.service;

import com.example.transportapi.entity.Trip;
import com.example.transportapi.payload.TripStatusUpdateRequest;

public interface TripService {

    Trip getUpcomingTripForUser(String username);

    Trip getTripById(Long id);

    Trip updateTripStatus(Long id, TripStatusUpdateRequest updateRequest);

    boolean verifyTrip(Long id, Integer verificationToken);
}
