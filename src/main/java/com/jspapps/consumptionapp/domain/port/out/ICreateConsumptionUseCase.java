package com.jspapps.consumptionapp.domain.port.out;

import com.jspapps.consumptionapp.domain.dto.SaveConsumption;

import java.util.List;

public interface ICreateConsumptionUseCase {

    void saveConsumption(List<SaveConsumption> consumptionList);
}
