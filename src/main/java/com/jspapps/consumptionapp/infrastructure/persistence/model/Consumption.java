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
import java.util.Objects;

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

    @Override
    public String toString() {
        return "Consumption{" +
                "id='" + id + '\'' +
                ", meter=" + meter +
                ", activeEnergy=" + activeEnergy +
                ", reactiveEnergy=" + reactiveEnergy +
                ", capacitiveReactive=" + capacitiveReactive +
                ", solar=" + solar +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Consumption that = (Consumption) o;
        return meter == that.meter && Objects.equals(id, that.id) && Objects.equals(activeEnergy, that.activeEnergy)
                && Objects.equals(reactiveEnergy, that.reactiveEnergy) && Objects.equals(capacitiveReactive, that.capacitiveReactive)
                && Objects.equals(solar, that.solar) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, meter, activeEnergy, reactiveEnergy, capacitiveReactive, solar, date);
    }
}
