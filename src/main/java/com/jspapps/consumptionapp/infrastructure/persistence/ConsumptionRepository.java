package com.jspapps.consumptionapp.infrastructure.persistence;

import com.jspapps.consumptionapp.infrastructure.persistence.model.Consumption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumptionRepository extends JpaRepository<Consumption, String> {
}
