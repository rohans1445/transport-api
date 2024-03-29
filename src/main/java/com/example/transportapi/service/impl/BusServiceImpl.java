package com.example.transportapi.service.impl;

import com.example.transportapi.dto.BusCreateDTO;
import com.example.transportapi.entity.Bus;
import com.example.transportapi.exception.ResourceNotFoundException;
import com.example.transportapi.repository.BusRepository;
import com.example.transportapi.service.BusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.transportapi.util.AppConstants.BUS_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class BusServiceImpl implements BusService {

    private final BusRepository busRepository;

    @Override
    public Bus getBusById(Long id) {
        Optional<Bus> bus = busRepository.findById(id);
        bus.orElseThrow(() -> new ResourceNotFoundException(BUS_NOT_FOUND + id));
        return bus.get();
    }

    @Override
    public List<Bus> getAllBusses() {
        return busRepository.findAll();
    }

    @Override
    public List<Bus> getAvailableBusses() {
        List<Bus> busList = getAllBusses();

        return busList.stream()
                .filter(bus -> bus.getRoute() == null)
                .collect(Collectors.toList());
    }

    @Override
    public Bus saveBus(BusCreateDTO busCreateDTO) {
        Bus bus = new Bus();
        bus.setBusNumber(busCreateDTO.getBusNumber());
        bus.setSeatingCapacity(busCreateDTO.getSeatingCapacity());
        return busRepository.save(bus);
    }

    @Override
    public boolean isBusAssignedToRouteOtherThanGivenRoute(Long busId, Long routeId) {
        return busRepository.isBusAssignedToRouteOtherThanGivenRoute(busId, routeId);
    }
}
