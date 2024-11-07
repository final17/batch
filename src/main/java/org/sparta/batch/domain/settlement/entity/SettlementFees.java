package org.sparta.batch.domain.settlement.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "settlement_fees")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SettlementFees {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "settlement_id", nullable = false)
    private Settlement settlement;

    private String type;        // 결제 수수료의 상세정보
    private Long supplyAmount;  // 결제 수수료의 공급가액
}
