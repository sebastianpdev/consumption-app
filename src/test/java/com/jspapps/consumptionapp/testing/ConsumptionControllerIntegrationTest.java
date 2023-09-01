package com.jspapps.consumptionapp.testing;

import com.jspapps.consumptionapp.ConsumptionappApplication;
import com.jspapps.consumptionapp.application.port.out.ConsumptionOutputPort;
import com.jspapps.consumptionapp.infrastructure.util.DateUtils;
import com.jspapps.consumptionapp.infrastructure.util.StringUtils;
import com.jspapps.consumptionapp.infrastructure.util.constant.KindPeriod;
import com.jspapps.consumptionapp.domain.model.ConsumptionDTO;
import com.jspapps.consumptionapp.domain.model.DataGraphDTO;
import com.jspapps.consumptionapp.application.port.in.IListConsumptionUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ConsumptionappApplication.class)
@AutoConfigureMockMvc
public class ConsumptionControllerIntegrationTest {

    @Autowired private MockMvc mockMvc;

    @MockBean private IListConsumptionUseCase listConsumptionUseCase;

    @MockBean private ConsumptionOutputPort consumptionOutputPort;

    // Variables necesarias para el test
    private List<Integer> meters;
    private static final String startDate = "2023-06-01";
    private static final String endDate = "2023-07-10";

    private DataGraphDTO dataGraphDTO;
    private static final String CONSUMPTION_ID_ONE = "1bcc9369";

    @BeforeEach
    public void init() {
        DataGraphDTO.DataGraph dataGraph = DataGraphDTO.DataGraph.builder()
                .meter_id(1).address("Street ".concat(StringUtils.generateStreetRandom())).active(List.of(new BigDecimal("17234.731809999997")))
                .reactive_capacitive(List.of(new BigDecimal("10516.07749"))).reactive_inductive(List.of(new BigDecimal("0")))
                .solar(List.of(new BigDecimal("0.6388935728544158")))
                .build();

        dataGraphDTO = DataGraphDTO.builder()
                .period(List.of("MAY. 2023"))
                .data_graph(List.of(dataGraph))
                .build();
        meters = List.of(1);
    }

    @Test
    void givenMetersAndDatesRangeAndPeriod_whenListConsumptionRecords_thenDataGraphFormat() throws Exception {
        var consumptionDTO = ConsumptionDTO.builder()
                .id(CONSUMPTION_ID_ONE).meter(1).activeEnergy(new BigDecimal("17234.731809999997")).reactiveEnergy(new BigDecimal("10516.07749"))
                .capacitiveReactive(new BigDecimal("0")).solar(new BigDecimal("0.6388935728544158")).date(convertStringToInstant(startDate))
                .build();

        var mStartDate = convertStringToInstant(startDate);
        var mEndDate = convertStringToInstant(endDate);

        // simulamos la ejecucion en la capa de acceso a datos
        given(consumptionOutputPort.listConsumptionRecords(meters, mStartDate, mEndDate)).willReturn(List.of(consumptionDTO));

        // simulamos la ejecucion en la capa de caso de uso
        given(listConsumptionUseCase.listConsumptionRecords(meters, startDate, endDate, KindPeriod.monthly)).willReturn(dataGraphDTO);

        // ejecutamos el endpoint para trear el listado de consumos
        mockMvc.perform(get("/consumption")
                .param("meters_ids","1").param("start_date", startDate).param("end_date", endDate).param("kind_period", KindPeriod.monthly.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.period").exists())
                .andExpect(jsonPath("$.period[0]").value("MAY. 2023"));

        // verificamos la ejecucion de metodo en el caso de uso
        verify(listConsumptionUseCase, times(1)).listConsumptionRecords(meters, startDate, endDate, KindPeriod.monthly);

        // reestabler estado del objeto simulado
        reset(listConsumptionUseCase);
        reset(consumptionOutputPort);
    }

    private Instant convertStringToInstant(String date) {
        return DateUtils.convertStringToInstant(date);
    }

}
