package com.example.transportapi;

import com.example.transportapi.dto.TripPassengersDTO;
import com.example.transportapi.entity.Trip;
import com.example.transportapi.repository.TripRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
class TransportApiApplicationTests {

	@Autowired
	TripRepository tripRepository;

	@Test
	@Transactional
	void contextLoads() {

		Trip trip = tripRepository.findById(38L).get();
		List<TripPassengersDTO> allPassengersInATrip = tripRepository.getAllPassengersInATrip(trip.getDate(), trip.getBusPass().getShift(), trip.getBusPass().getRoute().getId());
		System.out.println(allPassengersInATrip);

	}

}
