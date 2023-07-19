package com.jspapps.consumptionapp.infrastructure.web;

import com.jspapps.consumptionapp.application.util.constant.KindPeriod;
import com.jspapps.consumptionapp.domain.port.in.IListConsumptionUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class ConsumptionController {

    private final IListConsumptionUseCase listConsumptionUseCase;

    @GetMapping("/consumption")
    public ResponseEntity<Object> listConsumptionRecords(@RequestParam("meters_ids") List<Integer> metersId, @RequestParam("start_date") String startDate,
                                                         @RequestParam("end_date") String endDate, @RequestParam("kind_period") KindPeriod kindPeriod) {

        listConsumptionUseCase.listConsumptionRecords(metersId, startDate, endDate, kindPeriod);
        return ResponseEntity.accepted().body("null");
    }
}
