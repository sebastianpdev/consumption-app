package com.jspapps.consumptionapp.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataGraphDTO {

    private List<String> period;
    private List<DataGraph> data_graph;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataGraphDTO that = (DataGraphDTO) o;
        return Objects.equals(period, that.period) && Objects.equals(data_graph, that.data_graph);
    }

    @Override
    public int hashCode() {
        return Objects.hash(period, data_graph);
    }

    @Override
    public String toString() {
        return "DataGraphDTO{" +
                "period=" + period +
                ", data_graph=" + data_graph +
                '}';
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DataGraph {

        private int meter_id;
        private String address;
        private List<BigDecimal> active;
        private List<BigDecimal> reactive_inductive;
        private List<BigDecimal> reactive_capacitive;
        private List<BigDecimal> solar;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DataGraph dataGraph = (DataGraph) o;
            return meter_id == dataGraph.meter_id && Objects.equals(address, dataGraph.address) && Objects.equals(active, dataGraph.active)
                    && Objects.equals(reactive_inductive, dataGraph.reactive_inductive) && Objects.equals(reactive_capacitive, dataGraph.reactive_capacitive)
                    && Objects.equals(solar, dataGraph.solar);
        }

        @Override
        public int hashCode() {
            return Objects.hash(meter_id, address, active, reactive_inductive, reactive_capacitive, solar);
        }

        @Override
        public String toString() {
            return "DataGraph{" +
                    "meter_id=" + meter_id +
                    ", address='" + address + '\'' +
                    ", active=" + active +
                    ", reactive_inductive=" + reactive_inductive +
                    ", reactive_capacitive=" + reactive_capacitive +
                    ", solar=" + solar +
                    '}';
        }
    }

}
