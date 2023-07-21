package com.jspapps.consumptionapp.domain.port.out;

import com.jspapps.consumptionapp.domain.dto.ConsumptionDTO;

import java.time.Instant;
import java.util.List;

public interface IListConsumptionDAO {

    List<ConsumptionDTO> listConsumptionRecords(List<Integer> metersId, Instant startDate, Instant endDate);
}
