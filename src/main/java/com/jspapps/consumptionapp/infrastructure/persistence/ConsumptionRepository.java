package com.jspapps.consumptionapp.infrastructure.persistence;

import com.jspapps.consumptionapp.infrastructure.persistence.model.Consumption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
public interface ConsumptionRepository extends JpaRepository<Consumption, String> {

    List<Consumption> findAllByMeter(@NotNull int meter);
}
