package com.jspapps.consumptionapp.application.port.in;

import com.jspapps.consumptionapp.infrastructure.util.constant.KindPeriod;
import com.jspapps.consumptionapp.domain.model.DataGraphDTO;

import java.util.List;

public interface IListConsumptionUseCase {

    DataGraphDTO listConsumptionRecords(List<Integer> metersId, String startDate, String endDate, KindPeriod kindPeriod);
}
