package org.sparta.batch.domain.settlement.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.sparta.batch.domain.settlement.enums.SummaryType;

import java.time.LocalDate;

@NoArgsConstructor
@Setter
@Getter
public class SettlementSummaryDto {
    private LocalDate summaryDate;  // 집계 기준 날짜
    private SummaryType type;
    private Long totalAmount;       // 집계된 총 결제 금액
    private Long totalFee;          // 집계된 총 수수료
    private Long totalTransactions; // 집계된 총 거래수
}
