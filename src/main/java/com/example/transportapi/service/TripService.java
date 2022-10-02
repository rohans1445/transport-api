package com.example.transportapi.service;

import com.example.transportapi.entity.Trip;

public interface TripService {
    Trip getUpcomingTripForUser(String username);
}
