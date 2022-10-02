package com.example.transportapi.service.impl;

import com.example.transportapi.dto.BusPassCreateDTO;
import com.example.transportapi.dto.BusPassResponseDTO;
import com.example.transportapi.entity.*;
import com.example.transportapi.entity.enums.BusPassStatus;
import com.example.transportapi.entity.enums.BusPassType;
import com.example.transportapi.exception.InvalidInputException;
import com.example.transportapi.exception.ResourceNotFoundException;
import com.example.transportapi.repository.BusPassRepository;
import com.example.transportapi.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.transportapi.entity.enums.BusPassStatus.ACTIVE;
import static com.example.transportapi.entity.enums.BusPassType.*;
import static com.example.transportapi.entity.enums.BusPassType.HYBRID;
import static com.example.transportapi.util.AppConstants.*;

@Service
@RequiredArgsConstructor
public class BusPassServiceImpl implements BusPassService {

    private final BusPassRepository busPassRepository;
    private final RouteService routeService;
    private final OfficeLocationService officeLocationService;
    private final UserService userService;
    private final StopService stopService;


    @Override
    public BusPass saveBusPass(BusPassCreateDTO busPassCreateDTO) {

        User currentUser = userService.getCurrentUser();
        OfficeLocation officeLocation = officeLocationService.getOfficeLocationById(busPassCreateDTO.getOfficeLocationId());
        Route route = routeService.getRouteById(busPassCreateDTO.getRouteId());
        Stop pickupPoint = stopService.getStopById(busPassCreateDTO.getPickupPointId());

        // handle duplicate bus passes - for HYBRID & MONTHLY
        if((busPassCreateDTO.getBusPassType().equals(HYBRID) ||
                busPassCreateDTO.getBusPassType().equals(MONTHLY)) &&
                checkIfBusPassExistsForGivenMonth(busPassCreateDTO.getMonth())){
            throw new InvalidInputException(
                    String.format("Bus pass of type [%s] already exists for month [%s]. Consider updating existing bus pass.", busPassCreateDTO.getBusPassType(), busPassCreateDTO.getMonth())
            );
        }


        // handle monthly passes
        if(busPassCreateDTO.getBusPassType().equals(MONTHLY)){
            List<LocalDate> datesForMonth = getDatesForMonth(busPassCreateDTO.getMonth());
            busPassCreateDTO.setSelectedDates(datesForMonth);
        }

        // check for invalid dates
        if(busPassCreateDTO.getSelectedDates() == null || busPassCreateDTO.getSelectedDates().isEmpty() || !areDatesValid(busPassCreateDTO.getSelectedDates())){
            throw new InvalidInputException("Date selected are not valid.");
        }

        // check if the given route exists for the given office location
        if(!officeLocation.getRoutes().contains(route)){
            throw new InvalidInputException("The provided route does not exist within the provided office location.");
        }

        // check if the given pickup point exists for the given route
        if(!route.getStops().contains(pickupPoint)){
            throw new InvalidInputException("The provided pick up point does not exist for the provided route.");
        }

        // check if user has selected > 1 dates for bus pass type SINGLE
        if(busPassCreateDTO.getBusPassType().equals(SINGLE) &&
                busPassCreateDTO.getSelectedDates().size() > 1){
            throw new InvalidInputException("Cannot select more than 1 date for bus pass type [SINGLE]");
        }

        // check if user has selected dates other than given month for bus pass type HYBRID
        if(busPassCreateDTO.getBusPassType().equals(HYBRID) &&
                !areDatesFromGivenMonth(busPassCreateDTO.getSelectedDates(), busPassCreateDTO.getMonth())){
            throw new InvalidInputException("Dates selected are not from the given month ["+ busPassCreateDTO.getMonth() +"]");
        }

        // prevent booking SINGLE for given month - if user has booked HYBRID or MONTHLY
        if(busPassCreateDTO.getBusPassType().equals(SINGLE) && checkIfBusPassExistsForGivenMonth(busPassCreateDTO.getMonth())){
            throw new InvalidInputException("Cannot book bus pass [SINGLE] as [HYBRID] or [MONTHLY] pass already exists for the month ["+busPassCreateDTO.getMonth()+"]. Consider updating the existing pass.");
        }

        // prevent booking HYBRID - if selected dates are less than set amount
    if(busPassCreateDTO.getBusPassType().equals(HYBRID) && busPassCreateDTO.getSelectedDates().size() < MINIMUM_DAYS_REQUIRED_FOR_HYBRID_PASS){
            throw new InvalidInputException(String.format("Must select atleast [%d] days for pass type [HYBRID]", MINIMUM_DAYS_REQUIRED_FOR_HYBRID_PASS));
        }

        BusPass busPass = BusPass.builder()
                .officeLocation(officeLocation)
                .route(route)
                .pickupPoint(pickupPoint)
                .busPassType(busPassCreateDTO.getBusPassType())
                .shift(busPassCreateDTO.getShift())
                .status(ACTIVE)
                .cost(calculateCost(busPassCreateDTO))
                .tripType(busPassCreateDTO.getTripType())
                .month(busPassCreateDTO.getMonth())
                .user(currentUser)
                .build();

        // convert date objects to Trip objects
        List<Trip> bookedTrips = busPassCreateDTO.getSelectedDates()
                .stream()
                .map(date -> Trip.builder()
                        .busPass(busPass)
                        .userHasBoarded(false)
                        .date(date)
                        .build())
                .collect(Collectors.toList());


        busPass.setTrips(bookedTrips);


        return busPassRepository.save(busPass);
    }

    private List<LocalDate> getDatesForMonth(Month month) {
        LocalDate now = LocalDate.now();
        int length = month.length(false);
        List<LocalDate> result = new ArrayList<>();
        for(int day = 1; day <= length; day++){
            result.add(LocalDate.of(now.getYear(), month.getValue(), day));
        }
        return result;
    }

    private boolean areDatesValid(List<LocalDate> selectedDates) {
        LocalDate now = LocalDate.now();
        for(LocalDate date : selectedDates){
            if(date.getYear() != now.getYear() || date.isBefore(now)){
                return false;
            }
        }
        return true;
    }

    private boolean areDatesFromGivenMonth(List<LocalDate> selectedDates, Month month) {
        for(LocalDate date : selectedDates){
            if(!date.getMonth().equals(month)){
                return false;
            }
        }
        return true;
    }

    private boolean checkIfBusPassExistsForGivenMonth(Month month){
        User currentUser = userService.getCurrentUser();
        List<BusPassResponseDTO> currentUserPasses = userService.getUserPasses(currentUser.getUsername());
        for(BusPassResponseDTO pass : currentUserPasses){
            if((pass.getBusPassType().equals(HYBRID) || pass.getBusPassType().equals(MONTHLY))
                && pass.getMonth().equals(month)){
                return true;
            }
        }
        return false;
    }

    private Integer calculateCost(BusPassCreateDTO busPassCreateDTO) {
        if(busPassCreateDTO.getBusPassType().equals(MONTHLY)){
            return busPassCreateDTO.getSelectedDates().size() * PER_TRIP_COST_MONTHLY;
        }
        return busPassCreateDTO.getSelectedDates().size() * PER_TRIP_COST;
    }

    @Override
    public BusPass getBusPassById(Long id) {
        Optional<BusPass> busPassOptional = busPassRepository.findById(id);
        busPassOptional.orElseThrow(() -> new ResourceNotFoundException("Couldn't find Buss Pass with id - " + id));
        return busPassOptional.get();
    }

    @Override
    public BusPass getCurrentActiveBusPassForUser(String username) {
        User user = userService.findByUsername(username);
        Month currentMonth = LocalDate.now().getMonth();

        return user.getPasses().stream()
                .filter(pass -> pass.getStatus().equals(ACTIVE) && pass.getMonth().equals(currentMonth))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No active bus pass for given user"));
    }

}
