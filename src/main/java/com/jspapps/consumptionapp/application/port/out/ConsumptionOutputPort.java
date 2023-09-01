package com.jspapps.consumptionapp.application.port.out;

import com.jspapps.consumptionapp.domain.model.ConsumptionDTO;

import java.time.Instant;
import java.util.List;

public interface ConsumptionOutputPort {

    void saveConsumption(List<ConsumptionDTO> consumptionList);
    List<ConsumptionDTO> listConsumptionRecords(List<Integer> metersId, Instant startDate, Instant endDate);
}
