package org.sparta.batch.domain.settlement.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class SettlementFeesDto {
    private String type;        // 결제 수수료의 상세정보
    private Long supplyAmount;  // 결제 수수료의 공급가액
}
