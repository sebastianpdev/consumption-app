package com.jspapps.consumptionapp.domain.port.out;

import com.jspapps.consumptionapp.domain.dto.ConsumptionDTO;

import java.util.List;

public interface IListConsumptionPort {

    List<ConsumptionDTO> listConsumptionRecords(List<Integer> metersId, String startDate, String endDate);
}
