package org.sparta.batch.domain.waiting.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sparta.batch.domain.waiting.dto.DailyWaitingStatisticsDto;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class DailyWaitingStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long storeId;
    private LocalDate date; // 통계 날짜

    private long totalWaitingCount; // 하루 동안 총 대기 인원 수
    private long completedCount; // 완료된 대기 건수
    private long canceledCount; // 취소된 대기 건수


    private double averageWaitingTime; // 하루 평균 대기 시간

    private double cancellationRate; // 취소율 (퍼센트)

    private LocalDateTime createdAt;

    @Builder
    public DailyWaitingStatistics(Long storeId, LocalDate date, int totalWaitingCount, int completedCount, int canceledCount, double averageWaitingTime, double cancellationRate, LocalDateTime createdAt) {
        this.storeId = storeId;
        this.date = date;
        this.totalWaitingCount = totalWaitingCount;
        this.completedCount = completedCount;
        this.canceledCount = canceledCount;
        this.averageWaitingTime = averageWaitingTime;
        this.cancellationRate = cancellationRate;
        this.createdAt = LocalDateTime.now();
    }

    public DailyWaitingStatistics(DailyWaitingStatisticsDto dto) {
        this.storeId = dto.getStoreId();
        this.date = dto.getDate().toLocalDate();
        this.totalWaitingCount = dto.getTotalWaitingCount();
        this.completedCount = dto.getCompletedCount();
        this.canceledCount = dto.getCanceledCount();
        this.averageWaitingTime = dto.getAverageWaitingTime();
        this.cancellationRate = this.totalWaitingCount > 0
                ? (double) this.canceledCount * 100 / this.totalWaitingCount
                : 0.0;
        this.createdAt = LocalDateTime.now();
    }
}

