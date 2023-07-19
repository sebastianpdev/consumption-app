package com.jspapps.consumptionapp.domain.port.in;

import com.jspapps.consumptionapp.application.util.constant.KindPeriod;

import java.util.List;

public interface IListConsumptionUseCase {

    void listConsumptionRecords(List<Integer> metersId, String startDate, String endDate, KindPeriod kindPeriod);
}
