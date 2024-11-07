package org.sparta.batch.domain.settlement.repository;

import org.sparta.batch.domain.settlement.entity.SettlementSummary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettlementSummaryRepository extends JpaRepository<SettlementSummary, Long> {
}
