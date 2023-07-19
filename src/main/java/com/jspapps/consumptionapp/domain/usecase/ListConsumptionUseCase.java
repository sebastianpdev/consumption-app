package com.jspapps.consumptionapp.domain.usecase;

import com.jspapps.consumptionapp.application.util.annotation.UseCase;
import com.jspapps.consumptionapp.application.util.constant.KindPeriod;
import com.jspapps.consumptionapp.domain.dto.ConsumptionDTO;
import com.jspapps.consumptionapp.domain.port.in.IListConsumptionUseCase;
import com.jspapps.consumptionapp.domain.port.out.IListConsumptionPort;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@UseCase
public class ListConsumptionUseCase implements IListConsumptionUseCase {

    private final IListConsumptionPort listConsumptionPort;

    @Override
    public void listConsumptionRecords(List<Integer> metersId, String startDate, String endDate, KindPeriod kindPeriod) {

        var consumptionList = listConsumptionPort.listConsumptionRecords(metersId, startDate, endDate);
        var dates = consumptionList.stream().map(ConsumptionDTO::getDate).collect(Collectors.toList());

        KindPeriodHandle.KindPeriodContext periodContext = new KindPeriodHandle.KindPeriodContext();

        switch (kindPeriod) {
            case monthly:
                periodContext.setPeriodFormatter(new KindPeriodHandle.PeriodMonthlyFormat());
                periodContext.prepareDates(dates);
                periodContext.buildPeriodSection();
                break;

            case weekly:
                periodContext.setPeriodFormatter(new KindPeriodHandle.PeriodWeeklyFormat());
                periodContext.prepareDates(dates);
                periodContext.buildPeriodSection();
                break;

            case daily:
                periodContext.setPeriodFormatter(new KindPeriodHandle.PeriodDailyFormat());
                periodContext.prepareDates(dates);
                periodContext.buildPeriodSection();
                break;
        }
    }
}
