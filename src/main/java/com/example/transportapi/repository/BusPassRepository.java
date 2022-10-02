package com.example.transportapi.repository;

import com.example.transportapi.entity.BusPass;
import com.example.transportapi.entity.enums.BusPassStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusPassRepository extends JpaRepository<BusPass, Long> {

    List<BusPass> findAllByStatus(BusPassStatus status);

}
