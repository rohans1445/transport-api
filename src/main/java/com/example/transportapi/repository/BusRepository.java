package com.example.transportapi.repository;

import com.example.transportapi.entity.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BusRepository extends JpaRepository<Bus, Long> {

    @Query("SELECT CASE WHEN COUNT(r)>0 " +
            "THEN TRUE ELSE FALSE END " +
            "FROM Route r WHERE r.bus.id = :busId and r.id != :routeId")
    boolean isBusAssignedToRouteOtherThanGivenRoute(Long busId, Long routeId);

}
