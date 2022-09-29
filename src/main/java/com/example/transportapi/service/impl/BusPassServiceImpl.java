package com.example.transportapi.service.impl;

import com.example.transportapi.dto.BusPassCreateDTO;
import com.example.transportapi.entity.BookedDate;
import com.example.transportapi.entity.BusPass;
import com.example.transportapi.entity.OfficeLocation;
import com.example.transportapi.entity.Route;
import com.example.transportapi.entity.enums.BusPassStatus;
import com.example.transportapi.exception.ResourceNotFoundException;
import com.example.transportapi.repository.BusPassRepository;
import com.example.transportapi.service.BusPassService;
import com.example.transportapi.service.OfficeLocationService;
import com.example.transportapi.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BusPassServiceImpl implements BusPassService {

    private final BusPassRepository busPassRepository;
    private final RouteService routeService;
    private final OfficeLocationService officeLocationService;


    @Override
    public BusPass saveBusPass(BusPassCreateDTO busPassCreateDTO) {

        OfficeLocation officeLocation = officeLocationService.getOfficeLocationById(busPassCreateDTO.getOfficeLocationId());
        Route route = routeService.getRouteById(busPassCreateDTO.getRouteId());

        BusPass busPass = BusPass.builder()
                .officeLocation(officeLocation)
                .route(route)
                .busPassType(busPassCreateDTO.getBusPassType())
                .shift(busPassCreateDTO.getShift())
                .status(BusPassStatus.PENDING)
                .tripType(busPassCreateDTO.getTripType())
                .build();

        List<BookedDate> bookedDates = busPassCreateDTO.getSelectedDates()
                .stream()
                .map(date -> new BookedDate(busPass, date))
                .collect(Collectors.toList());

        busPass.setBookedDates(bookedDates);


        return busPassRepository.save(busPass);
    }

    @Override
    public BusPass getBusPassById(Long id) {
        Optional<BusPass> busPassOptional = busPassRepository.findById(id);
        busPassOptional.orElseThrow(() -> new ResourceNotFoundException("Couldn't find Buss Pass with id - " + id));
        return busPassOptional.get();
    }

}
