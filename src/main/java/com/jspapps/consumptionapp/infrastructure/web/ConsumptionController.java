package com.jspapps.consumptionapp.infrastructure.web;

import com.jspapps.consumptionapp.domain.service.ConsumptionService;
import com.jspapps.consumptionapp.infrastructure.util.constant.KindPeriod;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class ConsumptionController {

    private static final String PARAM_METER = "meters_ids";
    private static final String PARAM_START_DATE = "start_date";
    private static final String PARAM_END_DATE = "end_date";
    private static final String PARAM_PERIOD = "kind_period";
    private static final String URL_BASE_CONSUMPTION = "/consumption";


    private final ConsumptionService consumptionService;

    @GetMapping(value = URL_BASE_CONSUMPTION, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> listConsumptionRecords(@RequestParam(PARAM_METER) List<Integer> metersId, @RequestParam(PARAM_START_DATE) String startDate,
                                                         @RequestParam(PARAM_END_DATE) String endDate, @RequestParam(PARAM_PERIOD) KindPeriod kindPeriod) {

        var dataGraph = consumptionService.listConsumptionRecords(metersId, startDate, endDate, kindPeriod);
        return ResponseEntity.accepted().body(dataGraph);
    }
}
