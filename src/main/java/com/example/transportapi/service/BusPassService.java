package com.example.transportapi.service;

import com.example.transportapi.dto.BusPassCreateDTO;
import com.example.transportapi.entity.BusPass;

public interface BusPassService {

    BusPass saveBusPass(BusPassCreateDTO busPassCreateDTO);

    BusPass getBusPassById(Long id);

    BusPass getCurrentActiveBusPassForUser(String username);
}
