package com.jspapps.consumptionapp.domain.port.in;

import com.jspapps.consumptionapp.application.util.constant.KindPeriod;
import com.jspapps.consumptionapp.domain.dto.DataGraphDTO;

import java.util.List;

public interface IListConsumptionUseCase {

    DataGraphDTO listConsumptionRecords(List<Integer> metersId, String startDate, String endDate, KindPeriod kindPeriod);
}
