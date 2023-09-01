package com.jspapps.consumptionapp.infrastructure.persistence.repository;

import com.jspapps.consumptionapp.infrastructure.persistence.model.Consumption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;

@Repository
public interface ConsumptionRepository extends JpaRepository<Consumption, String> {

    @Query(value = "select c from Consumption c where c.meter in (?1) and c.date between ?2 and ?3")
    List<Consumption> findAllByMeterIdsAndDateRange(@NotNull List<Integer> meter, @NotNull Instant startDate, @NotNull Instant endDate);
}
