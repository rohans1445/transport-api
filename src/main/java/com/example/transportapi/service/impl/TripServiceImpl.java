package com.example.transportapi.service.impl;

import com.example.transportapi.dto.TripPassengersDTO;
import com.example.transportapi.entity.Trip;
import com.example.transportapi.exception.ResourceNotFoundException;
import com.example.transportapi.payload.TripStatusUpdateRequest;
import com.example.transportapi.repository.TripRepository;
import com.example.transportapi.service.TripService;
import com.example.transportapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TripServiceImpl implements TripService {

    private final UserService userService;
    private final TripRepository tripRepository;

    @Override
    public Trip getTripById(Long id) {
        return tripRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Couldn't find Trip with id - " + id));
    }

    @Override
    public Trip updateTripStatus(Long id, TripStatusUpdateRequest updateRequest) {
        Trip trip = tripRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Couldn't find Trip with id - "+ id));
        trip.setStatus(updateRequest.getStatus());
        return tripRepository.save(trip);
    }

    @Override
    public boolean verifyTrip(Long id, Integer verificationToken) {
        Trip trip = getTripById(id);
        if(Objects.equals(verificationToken, trip.getVerificationToken())){
            trip.setTripVerified(true);
            tripRepository.save(trip);
            return true;
        }
        return false;
    }

    @Override
    public List<TripPassengersDTO> getAllUsersInATrip(Long id) {
        Trip trip = getTripById(id);
        return tripRepository.getAllPassengersInATrip(trip.getDate(),
                trip.getBusPass().getShift(),
                trip.getBusPass().getRoute().getId());
    }
}
