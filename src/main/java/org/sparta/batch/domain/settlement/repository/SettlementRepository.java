package org.sparta.batch.domain.settlement.repository;

import org.sparta.batch.domain.settlement.entity.Settlement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettlementRepository extends JpaRepository<Settlement, Long> {
}
