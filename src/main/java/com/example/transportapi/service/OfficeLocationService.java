package com.example.transportapi.service;

import com.example.transportapi.entity.OfficeLocation;

import java.util.List;

public interface OfficeLocationService {

    OfficeLocation getOfficeLocationById(Long id);

    OfficeLocation createOfficeLocation(OfficeLocation officeLocation);

    List<OfficeLocation> getAllOfficeLocations();

}
