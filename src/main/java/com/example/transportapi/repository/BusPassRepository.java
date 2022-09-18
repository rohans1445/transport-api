package com.example.transportapi.repository;

import com.example.transportapi.entity.BusPass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusPassRepository extends JpaRepository<BusPass, Long> {
}
