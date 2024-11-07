package org.sparta.batch.domain.settlement.entity.embeddables;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Fees {
    private String type;        // 결제 수수료의 상세정보
    private Long supplyAmount;  // 결제 수수료의 공급가액
}
