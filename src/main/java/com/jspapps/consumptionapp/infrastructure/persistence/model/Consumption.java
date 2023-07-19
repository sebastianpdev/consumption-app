package com.jspapps.consumptionapp.infrastructure.persistence.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "consumption")
public class Consumption implements Serializable {

    private static final long serialVersionUUID = 1L;

    @Id
    private String id;

    @NotNull
    @Column(name = "meter_id")
    private int meter;

    @NotNull
    @Column(name = "active_energy")
    private BigDecimal activeEnergy;

    @NotNull
    @Column(name = "reactive_energy")
    private BigDecimal reactiveEnergy;

    @NotNull
    @Column(name = "capacitive_reactive")
    private BigDecimal capacitiveReactive;

    @NotNull
    @Column(name = "solar")
    private BigDecimal solar;

    @NotNull
    @Column(name = "date")
    private Instant date;

}
