package org.sparta.batch.domain.settlement.repository;

import org.sparta.batch.domain.settlement.entity.Settlement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SettlementRepository extends JpaRepository<Settlement, Long> , SettlementDslRepository {
    @Query("SELECT s FROM Settlement s WHERE FUNCTION('DATE_FORMAT', s.approvedAt, '%Y-%m-%d') BETWEEN :startDt AND :endDt ORDER BY s.id ASC")
    List<Settlement> getSettlement(@Param("startDt") String startDt, @Param("endDt") String endDt);
}
