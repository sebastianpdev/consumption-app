package com.jspapps.consumptionapp.infrastructure.persistence.dao;

import com.jspapps.consumptionapp.application.port.out.ConsumptionOutputPort;
import com.jspapps.consumptionapp.domain.model.ConsumptionDTO;
import com.jspapps.consumptionapp.infrastructure.persistence.mapper.ConsumptionMapper;
import com.jspapps.consumptionapp.infrastructure.persistence.model.Consumption;
import com.jspapps.consumptionapp.infrastructure.persistence.repository.ConsumptionRepository;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ConsumptionPersistenceAdapter implements ConsumptionOutputPort {

    private final Logger logger = Logger.getLogger(ConsumptionPersistenceAdapter.class.getName());

    private final ConsumptionMapper consumptionMapper;
    private final ConsumptionRepository consumptionRepository;

    @Override
    public void saveConsumption(List<ConsumptionDTO> consumptionList) {
        logger.log(Level.INFO, "Trying to create consumption records ...");
        List<Consumption> newConsumption = consumptionList.stream().map(consumptionMapper::toEntity).collect(Collectors.toList());
        consumptionRepository.saveAll(newConsumption);

    }

    @Override
    public List<ConsumptionDTO> listConsumptionRecords(List<Integer> metersId, Instant startDate, Instant endDate) {
        return consumptionRepository.findAllByMeterIdsAndDateRange(metersId, startDate, endDate).stream().map(consumptionMapper::toDto).collect(Collectors.toList());
    }
}
