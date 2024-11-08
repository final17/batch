package org.sparta.batch.domain.settlement.repository;

import org.sparta.batch.domain.settlement.dto.SettlementSummaryDto;
import org.sparta.batch.domain.settlement.enums.SummaryType;

import java.util.List;

public interface SettlementDslRepository {
    List<SettlementSummaryDto> getSettlementSummary(SummaryType type , String startDate, String endDate);
}
