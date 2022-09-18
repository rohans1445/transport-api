package com.example.transportapi.repository;

import com.example.transportapi.entity.OfficeLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfficeLocationRepository extends JpaRepository<OfficeLocation, Long> {
}
