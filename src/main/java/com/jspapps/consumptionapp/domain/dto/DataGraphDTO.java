package com.jspapps.consumptionapp.domain.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataGraphDTO {

    private List<String> period;
    private List<DataGraph> data_graph;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DataGraph {

        private int meter_id;
        private String address;
        private List<BigDecimal> active;
        private List<BigDecimal> reactive_inductive;
        private List<BigDecimal> reactive_capacitive;
        private List<BigDecimal> solar;

    }

}
