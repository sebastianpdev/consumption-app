package com.jspapps.consumptionapp.domain.usecase;

import com.jspapps.consumptionapp.application.port.out.ConsumptionOutputPort;
import com.jspapps.consumptionapp.infrastructure.exception.CustomRuntimeException;
import com.jspapps.consumptionapp.infrastructure.util.DateUtils;
import com.jspapps.consumptionapp.infrastructure.util.annotation.UseCase;
import com.jspapps.consumptionapp.infrastructure.util.constant.AppConstant;
import com.jspapps.consumptionapp.infrastructure.util.constant.KindPeriod;
import com.jspapps.consumptionapp.domain.model.ConsumptionDTO;
import com.jspapps.consumptionapp.domain.model.DataGraphDTO;
import com.jspapps.consumptionapp.application.port.in.IListConsumptionUseCase;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@UseCase
public class ListConsumptionUseCase implements IListConsumptionUseCase {

    private final ConsumptionOutputPort consumptionOutputPort;

    @Override
    public DataGraphDTO listConsumptionRecords(List<Integer> metersId, String startDate, String endDate, KindPeriod kindPeriod) {
        try {
            var mStartDate = DateUtils.convertStringToInstant(startDate);
            var mEndDate = DateUtils.convertStringToInstant(endDate);

            var consumptionList = consumptionOutputPort.listConsumptionRecords(metersId, mStartDate, mEndDate);
            if (!consumptionList.isEmpty()) {
                DataGraphContext dataGraphContext = new DataGraphContext();
                dataGraphContext.buildDataGraph(consumptionList);
                dataGraphContext.processDataGraph();

                DataGraphDTO dataGraphDTO = new DataGraphDTO();
                dataGraphDTO.setData_graph(dataGraphContext.getDataGraphList());

                var dates = consumptionList.stream().map(ConsumptionDTO::getDate).collect(Collectors.toList());

                KindPeriodHandle.KindPeriodContext periodContext = new KindPeriodHandle.KindPeriodContext();

                switch (kindPeriod) {
                    case monthly:
                        periodContext.setPeriodFormatter(new KindPeriodHandle.PeriodMonthlyFormat());
                        periodContext.prepareDates(dates);
                        dataGraphDTO.setPeriod(periodContext.buildPeriodSection());
                        break;

                    case weekly:
                        periodContext.setPeriodFormatter(new KindPeriodHandle.PeriodWeeklyFormat());
                        periodContext.prepareDates(dates);
                        dataGraphDTO.setPeriod(periodContext.buildPeriodSection());
                        break;

                    case daily:
                        periodContext.setPeriodFormatter(new KindPeriodHandle.PeriodDailyFormat());
                        periodContext.prepareDates(dates);
                        dataGraphDTO.setPeriod(periodContext.buildPeriodSection());
                        break;
                }

                return dataGraphDTO;
            }

        } catch (Exception e) {
            throw new CustomRuntimeException(AppConstant.ERROR_LIST_CONSUMPTION_RECORDS_, AppConstant.ERROR_LIST_CONSUMPTION_RECORDS, e);
        }

        return null;
    }
}
