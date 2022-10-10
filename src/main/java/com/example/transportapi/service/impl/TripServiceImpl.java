package com.example.transportapi.service.impl;

import com.example.transportapi.entity.BusPass;
import com.example.transportapi.entity.Trip;
import com.example.transportapi.entity.User;
import com.example.transportapi.exception.ResourceNotFoundException;
import com.example.transportapi.payload.TripStatusUpdateRequest;
import com.example.transportapi.repository.TripRepository;
import com.example.transportapi.service.TripService;
import com.example.transportapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TripServiceImpl implements TripService {

    private final UserService userService;
    private final TripRepository tripRepository;

    @Override
    public Trip getUpcomingTripForUser(String username) {
        LocalDate now = LocalDate.now();
        User user = userService.findByUsername(username);

        List<BusPass> passes = user.getPasses();
        List<BusPass> collect = passes.stream()
                .filter(pass -> pass.getMonth().equals(now.getMonth()))
                .collect(Collectors.toList());

        return null;
    }

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
}
