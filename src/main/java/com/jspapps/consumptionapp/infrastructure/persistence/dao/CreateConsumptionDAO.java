package com.jspapps.consumptionapp.infrastructure.persistence.dao;

import com.jspapps.consumptionapp.application.util.annotation.PersistenceAdapter;
import com.jspapps.consumptionapp.domain.dto.SaveConsumption;
import com.jspapps.consumptionapp.domain.port.out.ICreateConsumptionUseCase;
import com.jspapps.consumptionapp.infrastructure.persistence.ConsumptionRepository;
import com.jspapps.consumptionapp.infrastructure.persistence.model.Consumption;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@AllArgsConstructor
@PersistenceAdapter
public class CreateConsumptionDAO implements ICreateConsumptionUseCase {

    private final Logger logger = Logger.getLogger(CreateConsumptionDAO.class.getName());
    private final ModelMapper modelMapper;

    private final ConsumptionRepository consumptionRepository;

    @Override
    public void saveConsumption(List<SaveConsumption> consumptionList) {
        logger.log(Level.INFO, "Trying to create consumption records ...");
        List<Consumption> newConsumption = consumptionList.stream().map(c -> modelMapper.map(c, Consumption.class)).collect(Collectors.toList());
        consumptionRepository.saveAll(newConsumption);
    }
}
