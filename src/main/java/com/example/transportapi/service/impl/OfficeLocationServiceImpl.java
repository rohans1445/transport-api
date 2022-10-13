package com.example.transportapi.service.impl;

import com.example.transportapi.entity.OfficeLocation;
import com.example.transportapi.exception.ResourceNotFoundException;
import com.example.transportapi.repository.OfficeLocationRepository;
import com.example.transportapi.service.OfficeLocationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.transportapi.util.AppConstants.LOCATION_NOT_FOUND;

@Service
public class OfficeLocationServiceImpl implements OfficeLocationService {

    private final OfficeLocationRepository officeLocationRepository;

    public OfficeLocationServiceImpl(OfficeLocationRepository officeLocationRepository) {
        this.officeLocationRepository = officeLocationRepository;
    }

    @Override
    public OfficeLocation getOfficeLocationById(Long id) {
        Optional<OfficeLocation> officeLocationOptional = officeLocationRepository.findById(id);
        officeLocationOptional.orElseThrow(() -> new ResourceNotFoundException(LOCATION_NOT_FOUND + id));
        return officeLocationOptional.get();
    }

    @Override
    public OfficeLocation createOfficeLocation(OfficeLocation officeLocation) {
        officeLocation.setRoutes(new ArrayList<>());
        return officeLocationRepository.save(officeLocation);
    }

    @Override
    public List<OfficeLocation> getAllOfficeLocations() {
        return officeLocationRepository.findAll();
    }
}
