package com.jspapps.consumptionapp.domain.usecase;

import com.jspapps.consumptionapp.domain.dto.ConsumptionDTO;
import com.jspapps.consumptionapp.domain.dto.DataGraphDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DataGraphContext {

    private final List<DataGraphDTO.DataGraph> dataGraphList = new ArrayList<>();
    private List<ConsumptionDTO> records;

    public void buildDataGraph(List<ConsumptionDTO> records) {
        this.records = records;
    }

    public void processDataGraph() {
        Map<Integer, List<ConsumptionDTO>> groupByMeter = records.stream().collect(Collectors.groupingBy(ConsumptionDTO::getMeter));
        groupByMeter.forEach((meter, consumptionList) -> {

            DataGraphDTO.DataGraph dataGraph = new DataGraphDTO.DataGraph();
            dataGraph.setMeter_id(meter);
            addActive(dataGraph, consumptionList);
            addReactiveEnergy(dataGraph, consumptionList);
            addCapacitiveReactive(dataGraph, consumptionList);
            addSolar(dataGraph, consumptionList);
            dataGraphList.add(dataGraph);
        });
    }

    public List<DataGraphDTO.DataGraph> getDataGraphList() {
        return this.dataGraphList;
    }

    private void addActive(DataGraphDTO.DataGraph dataGraph, List<ConsumptionDTO> consumptionList) {
        var activeList = consumptionList.stream().map(ConsumptionDTO::getActiveEnergy).collect(Collectors.toList());
        dataGraph.setActive(activeList);
    }

    private void addReactiveEnergy(DataGraphDTO.DataGraph dataGraph, List<ConsumptionDTO> consumptionList) {
        var reactiveList = consumptionList.stream().map(ConsumptionDTO::getReactiveEnergy).collect(Collectors.toList());
        dataGraph.setReactive_capacitive(reactiveList);
    }

    private void addCapacitiveReactive(DataGraphDTO.DataGraph dataGraph, List<ConsumptionDTO> consumptionList) {
        var capacitiveList = consumptionList.stream().map(ConsumptionDTO::getCapacitiveReactive).collect(Collectors.toList());
        dataGraph.setReactive_inductive(capacitiveList);
    }

    private void addSolar(DataGraphDTO.DataGraph dataGraph, List<ConsumptionDTO> consumptionList) {
        var solarList = consumptionList.stream().map(ConsumptionDTO::getSolar).collect(Collectors.toList());
        dataGraph.setSolar(solarList);
    }
}
