package com.jspapps.consumptionapp.infrastructure.persistence.mapper;

import com.jspapps.consumptionapp.domain.model.ConsumptionDTO;
import com.jspapps.consumptionapp.infrastructure.persistence.model.Consumption;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

@RequiredArgsConstructor
public class ConsumptionMapper {

    private final ModelMapper modelMapper;

    public Consumption toEntity(ConsumptionDTO consumptionDTO) {
        return modelMapper.map(consumptionDTO, Consumption.class);
    }

    public ConsumptionDTO toDto(Consumption consumption) {
        return modelMapper.map(consumption, ConsumptionDTO.class);
    }
}
