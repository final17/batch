package org.sparta.batch.domain.settlement.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.sparta.batch.domain.settlement.enums.SummaryType;
import org.sparta.batch.domain.store.entity.Store;
import org.sparta.batch.domain.user.entity.User;

@NoArgsConstructor
@Setter
@Getter
public class SettlementSummaryDto {
    private String summaryDate;  // 집계 기준 날짜
    private SummaryType type;
    private Long totalAmount;       // 집계된 총 결제 금액
    private Long totalFee;          // 집계된 총 수수료
    private Long totalTransactions; // 집계된 총 거래수
    private Long userId;
    private Long storeId;

    public SettlementSummaryDto(String summaryDate, Long totalAmount , Long totalFee , Long totalTransactions , Long userId, Long storeId) {
        this.summaryDate = summaryDate;
        this.totalAmount = totalAmount;
        this.totalFee = totalFee;
        this.totalTransactions = totalTransactions;
        this.userId = userId;
        this.storeId = storeId;
    }

    public void updateType(SummaryType type) {
        this.type = type;
    }
}
