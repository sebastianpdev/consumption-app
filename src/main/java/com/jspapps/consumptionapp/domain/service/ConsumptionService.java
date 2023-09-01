package com.jspapps.consumptionapp.domain.service;

import com.jspapps.consumptionapp.application.port.in.IListConsumptionUseCase;
import com.jspapps.consumptionapp.domain.model.DataGraphDTO;
import com.jspapps.consumptionapp.infrastructure.util.constant.KindPeriod;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ConsumptionService implements IListConsumptionUseCase {

    private final IListConsumptionUseCase listConsumptionUseCase;


    @Override
    public DataGraphDTO listConsumptionRecords(List<Integer> metersId, String startDate, String endDate, KindPeriod kindPeriod) {
        return listConsumptionUseCase.listConsumptionRecords(metersId, startDate, endDate, kindPeriod);
    }
}
