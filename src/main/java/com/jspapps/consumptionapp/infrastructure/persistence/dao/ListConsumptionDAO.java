package com.jspapps.consumptionapp.infrastructure.persistence.dao;

import com.jspapps.consumptionapp.application.util.annotation.PersistenceAdapter;
import com.jspapps.consumptionapp.domain.dto.ConsumptionDTO;
import com.jspapps.consumptionapp.domain.port.out.IListConsumptionPort;
import com.jspapps.consumptionapp.infrastructure.persistence.ConsumptionRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@PersistenceAdapter
public class ListConsumptionDAO implements IListConsumptionPort {

    private final ModelMapper modelMapper;
    private final ConsumptionRepository consumptionRepository;

    @Override
    public List<ConsumptionDTO> listConsumptionRecords(List<Integer> metersId, String startDate, String endDate) {
        return consumptionRepository.findAllByMeter(1).stream().map(c -> modelMapper.map(c, ConsumptionDTO.class)).collect(Collectors.toList());
    }
}
