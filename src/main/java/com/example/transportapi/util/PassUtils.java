package com.example.transportapi.util;

import com.example.transportapi.entity.BusPass;
import com.example.transportapi.entity.Trip;
import com.example.transportapi.entity.enums.TripStatus;
import com.example.transportapi.repository.BusPassRepository;
import com.example.transportapi.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.transportapi.entity.enums.BusPassStatus.ACTIVE;
import static com.example.transportapi.entity.enums.BusPassStatus.EXPIRED;
import static com.example.transportapi.entity.enums.BusPassType.SINGLE;

@Component
@Slf4j
@RequiredArgsConstructor
public class PassUtils {

    private final BusPassRepository busPassRepository;
    private final TripRepository tripRepository;

    @Scheduled(fixedDelay = 3600000)
    @Transactional
    public void passCleanupJob(){
        List<BusPass> passes = busPassRepository.findAllByStatus(ACTIVE);
        LocalDate now = LocalDate.now();
        StringBuilder message = new StringBuilder("Updated passes to [EXPIRED]: ");

        List<BusPass> updatedPasses = passes.stream()
                .peek(pass -> {
                    if (pass.getBusPassType().equals(SINGLE)) {
                        LocalDate date = pass.getTrips().get(0).getDate();
                        if (date.isBefore(now)) {
                            pass.setStatus(EXPIRED);
                            log.info("Pass ID [" + pass.getId() + "] set to [EXPIRED]");
                        }
                    } else {
                        if (pass.getMonth().getValue() < now.getMonth().getValue()) {
                            pass.setStatus(EXPIRED);
                            log.info("Pass ID [" + pass.getId() + "] set to [EXPIRED]");
                        }
                    }
                }).filter(pass -> pass.getStatus().equals(EXPIRED)).collect(Collectors.toList());

        if(!updatedPasses.isEmpty()){
            updatedPasses.forEach(pass -> message.append(pass.getId()).append(", "));
            log.info(String.valueOf(message));
        }

    }

    @Scheduled(fixedDelay = 3600000)
    @Transactional
    public void tripCleanupJob(){
        List<Trip> trips = tripRepository.findAllByStatus(TripStatus.ACTIVE);
        LocalDate now = LocalDate.now();
        StringBuilder message = new StringBuilder("Updated trips to [COMPLETED_WITHOUT_VERIFICATION]: ");

        List<Trip> updatedTrips = trips.stream()
                .peek(trip -> {
                    if (trip.getDate().isBefore(now) && !trip.isTripVerified()) {
                        trip.setStatus(TripStatus.COMPLETED_WITHOUT_VERIFICATION);
                        log.info("Pass ID [" + trip.getId() + "] set to [COMPLETED_WITHOUT_VERIFICATION]");
                    }
                }).filter(trip -> trip.getStatus().equals(TripStatus.COMPLETED_WITHOUT_VERIFICATION)).collect(Collectors.toList());

        if(!updatedTrips.isEmpty()){
            updatedTrips.forEach(trip -> message.append(trip.getId()).append(", "));
            log.info(String.valueOf(message));
        }

    }

}
