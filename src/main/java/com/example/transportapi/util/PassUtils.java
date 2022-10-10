package com.example.transportapi.util;

import com.example.transportapi.entity.BusPass;
import com.example.transportapi.entity.enums.BusPassStatus;
import com.example.transportapi.entity.enums.BusPassType;
import com.example.transportapi.repository.BusPassRepository;
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
import static com.example.transportapi.entity.enums.BusPassType.HYBRID;
import static com.example.transportapi.entity.enums.BusPassType.SINGLE;

@Component
@Slf4j
@RequiredArgsConstructor
public class PassUtils {

    private final BusPassRepository busPassRepository;

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

}
