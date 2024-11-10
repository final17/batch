package org.sparta.batch.domain.settlement.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sparta.batch.common.entity.Timestamped;
import org.sparta.batch.domain.settlement.enums.SummaryType;
import org.sparta.batch.domain.store.entity.Store;
import org.sparta.batch.domain.user.entity.User;

@Getter
@Entity
@Table(name = "settlement_summary")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SettlementSummary extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String summaryDate;  // 집계 기준 날짜

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private SummaryType type;

    @Column(nullable = false)
    private Long totalAmount; // 집계된 총 결제 금액

    @Column(nullable = false)
    private Long totalFee; // 집계된 총 수수료

    @Column(nullable = false)
    private Long totalTransactions; // 집계된 총 거래수

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    public SettlementSummary(String summaryDate , SummaryType type , Long totalAmount , Long totalFee , Long totalTransactions , User user , Store store) {
        this.summaryDate = summaryDate;
        this.type = type;
        this.totalAmount = totalAmount;
        this.totalFee = totalFee;
        this.totalTransactions = totalTransactions;
        this.user = user;
        this.store = store;
    }
}
