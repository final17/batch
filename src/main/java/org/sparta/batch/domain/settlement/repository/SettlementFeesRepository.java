package org.sparta.batch.domain.settlement.repository;

import org.sparta.batch.domain.settlement.entity.SettlementFees;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettlementFeesRepository extends JpaRepository<SettlementFees, Long> {
}
