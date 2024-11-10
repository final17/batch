package org.sparta.batch.domain.waiting.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sparta.batch.domain.store.entity.Store;
import org.sparta.batch.domain.waiting.dto.DailyWaitingStatisticsDto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class DailyWaitingStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="store_id")
    private Store store;

    private LocalDate date;

    private long totalWaitingCount;
    private long completedCount;
    private long canceledCount;


    private double completedAverageWaitingTime;
    private double canceledAverageWaitTime;

    private double cancellationRate; // 00%

    private LocalDateTime createdAt;

    @Builder
    public DailyWaitingStatistics(Store store, LocalDate date, int totalWaitingCount, int completedCount, int canceledCount, double completedAverageWaitingTime, double canceledAverageWaitTime, double cancellationRate, LocalDateTime createdAt) {
        this.store = store;
        this.date = date;
        this.totalWaitingCount = totalWaitingCount;
        this.completedCount = completedCount;
        this.canceledCount = canceledCount;
        this.completedAverageWaitingTime = completedAverageWaitingTime;
        this.canceledAverageWaitTime = canceledAverageWaitTime;
        this.cancellationRate = cancellationRate;
        this.createdAt = LocalDateTime.now();
    }

    public DailyWaitingStatistics(DailyWaitingStatisticsDto dto) {
        this.store = new Store(dto.getStoreId());
        this.date = dto.getDate().toLocalDate();
        this.totalWaitingCount = dto.getTotalWaitingCount();
        this.completedCount = dto.getCompletedCount();
        this.canceledCount = dto.getCanceledCount();
        this.completedAverageWaitingTime = BigDecimal.valueOf(dto.getCompletedAverageWaitingTime()).setScale(1, RoundingMode.HALF_UP).doubleValue();
        this.canceledAverageWaitTime =  BigDecimal.valueOf(dto.getCanceledAverageWaitTime()).setScale(1, RoundingMode.HALF_UP).doubleValue();
        this.cancellationRate = this.totalWaitingCount > 0
                ?  BigDecimal.valueOf((double) this.canceledCount * 100 / this.totalWaitingCount).setScale(2, RoundingMode.HALF_UP).doubleValue()
                : 0;
        this.createdAt = LocalDateTime.now();
    }
}

