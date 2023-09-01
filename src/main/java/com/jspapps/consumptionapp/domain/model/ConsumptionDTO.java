package com.jspapps.consumptionapp.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

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

    @Override
    public String toString() {
        return "ConsumptionDTO{" +
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
        ConsumptionDTO that = (ConsumptionDTO) o;
        return meter == that.meter && Objects.equals(id, that.id) && Objects.equals(activeEnergy, that.activeEnergy) && Objects.equals(reactiveEnergy, that.reactiveEnergy)
                && Objects.equals(capacitiveReactive, that.capacitiveReactive) && Objects.equals(solar, that.solar) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, meter, activeEnergy, reactiveEnergy, capacitiveReactive, solar, date);
    }
}
