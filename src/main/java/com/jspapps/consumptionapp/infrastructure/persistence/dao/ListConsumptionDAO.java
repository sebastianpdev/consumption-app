package com.jspapps.consumptionapp.infrastructure.persistence.dao;

import com.jspapps.consumptionapp.application.util.annotation.PersistenceAdapter;
import com.jspapps.consumptionapp.domain.dto.ConsumptionDTO;
import com.jspapps.consumptionapp.domain.port.out.IListConsumptionDAO;
import com.jspapps.consumptionapp.infrastructure.persistence.ConsumptionRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@PersistenceAdapter
public class ListConsumptionDAO implements IListConsumptionDAO {

    private final ModelMapper modelMapper;
    private final ConsumptionRepository consumptionRepository;

    @Override
    public List<ConsumptionDTO> listConsumptionRecords(List<Integer> metersId, Instant startDate, Instant endDate) {
        return consumptionRepository.findAllByMeterIdsAndDateRange(metersId, startDate, endDate).stream().map(c ->
                modelMapper.map(c, ConsumptionDTO.class)).collect(Collectors.toList());
    }
}
