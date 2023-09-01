package com.jspapps.consumptionapp.infrastructure.config;

import com.jspapps.consumptionapp.application.port.in.IListConsumptionUseCase;
import com.jspapps.consumptionapp.domain.service.ConsumptionService;
import com.jspapps.consumptionapp.infrastructure.persistence.dao.ConsumptionPersistenceAdapter;
import com.jspapps.consumptionapp.infrastructure.persistence.mapper.ConsumptionMapper;
import com.jspapps.consumptionapp.infrastructure.persistence.repository.ConsumptionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public ConsumptionMapper consumptionMapper(ModelMapper modelMapper) {
        return new ConsumptionMapper(modelMapper);
    }

    @Bean
    public ConsumptionPersistenceAdapter consumptionPersistenceAdapter(ConsumptionMapper consumptionMapper, ConsumptionRepository consumptionRepository) {
        return new ConsumptionPersistenceAdapter(consumptionMapper, consumptionRepository);
    }

    @Bean
    public ConsumptionService consumptionService(IListConsumptionUseCase listConsumptionUseCase) {
        return new ConsumptionService(listConsumptionUseCase);
    }

}
