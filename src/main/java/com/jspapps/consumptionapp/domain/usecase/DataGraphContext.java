package com.jspapps.consumptionapp.domain.usecase;

import com.jspapps.consumptionapp.infrastructure.util.StringUtils;
import com.jspapps.consumptionapp.domain.model.ConsumptionDTO;
import com.jspapps.consumptionapp.domain.model.DataGraphDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Clase encargada de controlar la informacion por medidor y prepararla para la respuesta del endpoint.
 */
public class DataGraphContext {

    private final List<DataGraphDTO.DataGraph> dataGraphList = new ArrayList<>();
    private List<ConsumptionDTO> records;

    public void buildDataGraph(List<ConsumptionDTO> records) {
        this.records = records;
    }

    /**
     * Gestiona la informacion por cada medidor
     */
    public void processDataGraph() {
        Map<Integer, List<ConsumptionDTO>> groupByMeter = records.stream().collect(Collectors.groupingBy(ConsumptionDTO::getMeter));
        groupByMeter.forEach((meter, consumptionList) -> {

            DataGraphDTO.DataGraph dataGraph = new DataGraphDTO.DataGraph();
            dataGraph.setMeter_id(meter);
            dataGraph.setAddress("Street ".concat(StringUtils.generateStreetRandom()));
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
