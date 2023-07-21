package com.jspapps.consumptionapp.domain.usecase;

import com.jspapps.consumptionapp.application.util.DateUtils;
import com.jspapps.consumptionapp.application.util.constant.KindPeriod;
import com.jspapps.consumptionapp.domain.dto.ConsumptionDTO;
import com.jspapps.consumptionapp.domain.dto.DataGraphDTO;
import com.jspapps.consumptionapp.domain.port.out.IListConsumptionDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListConsumptionUseCaseTest {

    private ListConsumptionUseCase listConsumptionUseCaseUnderTest;

    @Mock private IListConsumptionDAO listConsumptionDAO;

    // Variables necessary
    private List<Integer> meters;
    private static final String startDate = "2023-06-01";
    private static final String endDate = "2023-07-10";

    private ConsumptionDTO consumptionDTO, consumptionDTOTwo;
    private static final String CONSUMPTION_ID_ONE = "1bcc9369";
    private static final String CONSUMPTION_ID_TWO = "1ass4412";

    @BeforeEach
    public void init() {
        listConsumptionUseCaseUnderTest = new ListConsumptionUseCase(listConsumptionDAO);

        meters = List.of(1,2);

        consumptionDTO = ConsumptionDTO.builder()
                .id(CONSUMPTION_ID_ONE).meter(1).activeEnergy(new BigDecimal("0.00")).reactiveEnergy(new BigDecimal("0.00"))
                .capacitiveReactive(new BigDecimal("0.00")).solar(new BigDecimal("0.00")).date(convertStringToInstant(startDate))
                .build();

        consumptionDTOTwo = ConsumptionDTO.builder()
                .id(CONSUMPTION_ID_TWO).meter(2).activeEnergy(new BigDecimal("0.00")).reactiveEnergy(new BigDecimal("0.00"))
                .capacitiveReactive(new BigDecimal("0.00")).solar(new BigDecimal("0.00")).date(convertStringToInstant(endDate))
                .build();
    }

    @Test
    void givenMeterDatesAndPeriod_whenListConsumption_thenEmptyList() {
        Instant mStartDate = convertStringToInstant(startDate);
        Instant mEndDate = convertStringToInstant(endDate);

        lenient().when(listConsumptionDAO.listConsumptionRecords(meters, mStartDate, mEndDate)).thenReturn(Collections.emptyList());
        DataGraphDTO result = listConsumptionUseCaseUnderTest.listConsumptionRecords(meters, startDate, endDate, KindPeriod.monthly);
        Assertions.assertNull(result);
    }

    @Test
    void givenMeterDatesAndMonthlyPeriod_whenListConsumption_thenListRecords() {
        Instant mStartDate = convertStringToInstant(startDate);
        Instant mEndDate = convertStringToInstant(endDate);

        List<ConsumptionDTO> consumptionList = consumptionDTOList();
        when(listConsumptionDAO.listConsumptionRecords(meters, mStartDate, mEndDate)).thenReturn(consumptionList);
        DataGraphDTO result = listConsumptionUseCaseUnderTest.listConsumptionRecords(meters, startDate, endDate, KindPeriod.monthly);
        Assertions.assertNotNull(result);
    }

    @Test
    void givenMeterDatesAndWeeklyPeriod_whenListConsumption_thenListRecords() {
        Instant mStartDate = convertStringToInstant(startDate);
        Instant mEndDate = convertStringToInstant(endDate);

        List<ConsumptionDTO> consumptionList = consumptionDTOList();
        when(listConsumptionDAO.listConsumptionRecords(meters, mStartDate, mEndDate)).thenReturn(consumptionList);
        DataGraphDTO result = listConsumptionUseCaseUnderTest.listConsumptionRecords(meters, startDate, endDate, KindPeriod.weekly);
        Assertions.assertNotNull(result);
    }

    private Instant convertStringToInstant(String date) {
        return DateUtils.convertStringToInstant(date);
    }

    private List<ConsumptionDTO> consumptionDTOList() {
        return List.of(consumptionDTO, consumptionDTOTwo);
    }

}
