package com.example.transportapi.service.impl;

import com.example.transportapi.dto.BusPassCreateDTO;
import com.example.transportapi.entity.*;
import com.example.transportapi.entity.enums.BusPassStatus;
import com.example.transportapi.entity.enums.BusPassType;
import com.example.transportapi.exception.InvalidInputException;
import com.example.transportapi.exception.ResourceNotFoundException;
import com.example.transportapi.repository.BusPassRepository;
import com.example.transportapi.service.BusPassService;
import com.example.transportapi.service.OfficeLocationService;
import com.example.transportapi.service.RouteService;
import com.example.transportapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.transportapi.util.AppConstants.PER_TRIP_COST;

@Service
@RequiredArgsConstructor
public class BusPassServiceImpl implements BusPassService {

    private final BusPassRepository busPassRepository;
    private final RouteService routeService;
    private final OfficeLocationService officeLocationService;
    private final UserService userService;


    @Override
    public BusPass saveBusPass(BusPassCreateDTO busPassCreateDTO) {

        User currentUser = userService.getCurrentUser();
        OfficeLocation officeLocation = officeLocationService.getOfficeLocationById(busPassCreateDTO.getOfficeLocationId());
        Route route = routeService.getRouteById(busPassCreateDTO.getRouteId());




        // handle duplicate bus passes


        // check if the given route exists in the given office location
        if(!officeLocation.getRoutes().contains(route)){
            throw new InvalidInputException("The provided route does not exist within the provided office location.");
        }

        // check if user has selected > 1 dates for bus pass type SINGLE
        if(busPassCreateDTO.getBusPassType().equals(BusPassType.SINGLE) &&
                busPassCreateDTO.getSelectedDates().size() > 1){
            throw new InvalidInputException("Cannot select more than 1 date for bus pass type [SINGLE]");
        }

        // check if user has selected dates for multiple months for bus pass type HYBRID
        if(busPassCreateDTO.getBusPassType().equals(BusPassType.HYBRID) &&
                !areDatesFromSameMonth(busPassCreateDTO.getSelectedDates())){
            throw new InvalidInputException("Cannot select dates from more than 1 month for bus pass type [HYBRID]");
        }


        BusPass busPass = BusPass.builder()
                .officeLocation(officeLocation)
                .route(route)
                .busPassType(busPassCreateDTO.getBusPassType())
                .shift(busPassCreateDTO.getShift())
                .status(BusPassStatus.ACTIVE)
                .cost(calculateCost(busPassCreateDTO))
                .tripType(busPassCreateDTO.getTripType())
                .user(currentUser)
                .build();

        List<BookedDate> bookedDates = busPassCreateDTO.getSelectedDates()
                .stream()
                .map(date -> new BookedDate(busPass, date))
                .collect(Collectors.toList());

        busPass.setBookedDates(bookedDates);


        return busPassRepository.save(busPass);
    }

    private boolean areDatesFromSameMonth(List<LocalDate> selectedDates) {

        int firstDateMonthValue = selectedDates.get(0).getMonthValue();

        for(LocalDate date : selectedDates){
            if(date.getMonthValue() != firstDateMonthValue){
                return false;
            }
        }

        return true;
    }

    private Integer calculateCost(BusPassCreateDTO busPassCreateDTO) {
        return busPassCreateDTO.getSelectedDates().size() * PER_TRIP_COST;
    }

    @Override
    public BusPass getBusPassById(Long id) {
        Optional<BusPass> busPassOptional = busPassRepository.findById(id);
        busPassOptional.orElseThrow(() -> new ResourceNotFoundException("Couldn't find Buss Pass with id - " + id));
        return busPassOptional.get();
    }

}
