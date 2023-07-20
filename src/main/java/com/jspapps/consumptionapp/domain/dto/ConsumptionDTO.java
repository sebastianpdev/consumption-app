package com.jspapps.consumptionapp.domain.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConsumptionDTO {

    private String id;

    private int meter;

    private BigDecimal activeEnergy;

    private BigDecimal reactiveEnergy;

    private BigDecimal capacitiveReactive;

    private BigDecimal solar;

    private Instant date;
}
