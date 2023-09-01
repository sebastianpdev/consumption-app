package com.jspapps.consumptionapp.infrastructure.persistence.dao;

import com.jspapps.consumptionapp.infrastructure.util.DateUtils;
import com.jspapps.consumptionapp.domain.model.ConsumptionDTO;
import com.jspapps.consumptionapp.infrastructure.persistence.repository.ConsumptionRepository;
import com.jspapps.consumptionapp.infrastructure.persistence.model.Consumption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CreateConsumptionDAOTest {

    private CreateConsumptionDAO createConsumptionDAOUnderTest;

    @Mock private ConsumptionRepository consumptionRepository;

    private ModelMapper modelMapper;

    private ConsumptionDTO consumptionDTO, consumptionDTOTwo;
    private static final String CONSUMPTION_ID_ONE = "1bcc9369";
    private static final String CONSUMPTION_ID_TWO = "1ass4412";
    private static final String DATE_VALUE = "2023-06-01";

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);

        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.getConfiguration().setAmbiguityIgnored(true);

        createConsumptionDAOUnderTest = new CreateConsumptionDAO(modelMapper, consumptionRepository);

        consumptionDTO = ConsumptionDTO.builder()
                .id(CONSUMPTION_ID_ONE).meter(1).activeEnergy(new BigDecimal("0.00")).reactiveEnergy(new BigDecimal("0.00"))
                .capacitiveReactive(new BigDecimal("0.00")).solar(new BigDecimal("0.00")).date(convertStringToInstant())
                .build();

        consumptionDTOTwo = ConsumptionDTO.builder()
                .id(CONSUMPTION_ID_TWO).meter(2).activeEnergy(new BigDecimal("0.00")).reactiveEnergy(new BigDecimal("0.00"))
                .capacitiveReactive(new BigDecimal("0.00")).solar(new BigDecimal("0.00")).date(convertStringToInstant())
                .build();
    }

    @Test
    void givenConsumptionList_whenSaveAll_thenContinue() {

        List<ConsumptionDTO> consumptionDTOList = getConsumptionDTOList();
        List<Consumption> newConsumption = consumptionDTOList.stream().map(c -> modelMapper.map(c, Consumption.class)).collect(Collectors.toList());

        when(consumptionRepository.saveAll(newConsumption)).thenReturn(newConsumption);

        createConsumptionDAOUnderTest.saveConsumption(consumptionDTOList);

        verify(consumptionRepository, times(1)).saveAll(newConsumption);

        assertEquals(consumptionDTOList.size(), newConsumption.size());

        Consumption consumption1 = newConsumption.get(0);
        assertEquals(consumptionDTOList.get(0).getId(), consumption1.getId());
    }

    private Instant convertStringToInstant() {
        return DateUtils.convertStringToInstant(CreateConsumptionDAOTest.DATE_VALUE);
    }

    private List<ConsumptionDTO> getConsumptionDTOList() {
        return List.of(consumptionDTO, consumptionDTOTwo);
    }
}
