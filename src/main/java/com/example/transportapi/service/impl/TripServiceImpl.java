package com.example.transportapi.service.impl;

import com.example.transportapi.dto.TripPassengersDTO;
import com.example.transportapi.entity.Trip;
import com.example.transportapi.entity.enums.TripStatus;
import com.example.transportapi.exception.InvalidInputException;
import com.example.transportapi.exception.ResourceNotFoundException;
import com.example.transportapi.payload.TripStatusUpdateRequest;
import com.example.transportapi.repository.TripRepository;
import com.example.transportapi.service.TripService;
import com.example.transportapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

import static com.example.transportapi.entity.enums.TripStatus.CANCELLED;
import static com.example.transportapi.util.AppConstants.CUTOFF_TIME_FOR_TRIP_CANCELLATION_IN_HOURS;
import static com.example.transportapi.util.AppConstants.TRIP_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class TripServiceImpl implements TripService {

    private final UserService userService;
    private final TripRepository tripRepository;

    @Override
    public Trip getTripById(Long id) {
        return tripRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(TRIP_NOT_FOUND + id));
    }

    @Override
    public Trip updateTripStatus(Long id, TripStatusUpdateRequest updateRequest) {
        Trip trip = tripRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(TRIP_NOT_FOUND + id));

        if(updateRequest.getStatus().equals(CANCELLED)){
            if(!checkCutoffTimeForTripCancellation(trip)){
                throw new InvalidInputException(String.format("Cannot cancel a trip within [%d] hours of journey start time.", CUTOFF_TIME_FOR_TRIP_CANCELLATION_IN_HOURS));
            }
            trip.setStatus(CANCELLED);
            return tripRepository.save(trip);
        }

        trip.setStatus(updateRequest.getStatus());
        return tripRepository.save(trip);
    }

    private boolean checkCutoffTimeForTripCancellation(Trip trip) {
        LocalDateTime now = LocalDateTime.now();
        LocalDate date = trip.getDate();
        LocalTime shiftStartTime = LocalTime.parse(trip.getBusPass().getShift().getStartTime());
        LocalDateTime shiftStartTimeWithTripDate = shiftStartTime.atDate(date);

        // if current time is before the cutoff time for cancellation
        return now.isBefore(shiftStartTimeWithTripDate.minusHours(CUTOFF_TIME_FOR_TRIP_CANCELLATION_IN_HOURS));
    }

    @Override
    public boolean verifyTrip(Long id, Integer verificationToken) {
        Trip trip = getTripById(id);

        if(trip.getStatus().equals(CANCELLED)){
            throw new InvalidInputException("Cannot verify a cancelled trip.");
        }

        if(Objects.equals(verificationToken, trip.getVerificationToken())){
            trip.setTripVerified(true);
            trip.setStatus(TripStatus.COMPLETED);
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
