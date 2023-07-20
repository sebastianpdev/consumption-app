package com.jspapps.consumptionapp.domain.port.out;

import com.jspapps.consumptionapp.domain.dto.ConsumptionDTO;

import java.util.List;

public interface ICreateConsumptionUseCase {

    void saveConsumption(List<ConsumptionDTO> consumptionList);
}
