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

@Service
@RequiredArgsConstructor
public class BusServiceImpl implements BusService {

    private final BusRepository busRepository;

    @Override
    public Bus getBusById(Long id) {
        Optional<Bus> bus = busRepository.findById(id);
        bus.orElseThrow(() -> new ResourceNotFoundException("Couldn't find bus with id - " + id));
        return bus.get();
    }

    @Override
    public List<Bus> getAllBusses() {
        return busRepository.findAll();
    }

    @Override
    public Bus saveBus(BusCreateDTO busCreateDTO) {
        Bus bus = new Bus();
        bus.setBusNumber(busCreateDTO.getBusNumber());
        bus.setSeatingCapacity(busCreateDTO.getSeatingCapacity());
        return busRepository.save(bus);
    }
}
