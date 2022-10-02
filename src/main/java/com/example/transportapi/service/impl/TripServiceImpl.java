package com.example.transportapi.service.impl;

import com.example.transportapi.entity.BusPass;
import com.example.transportapi.entity.Trip;
import com.example.transportapi.entity.User;
import com.example.transportapi.service.TripService;
import com.example.transportapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TripServiceImpl implements TripService {

    private final UserService userService;

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
}
