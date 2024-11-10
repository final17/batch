package org.sparta.batch.domain.waiting.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HourlyStatisticsDto {
    private Long storeId;
    private int hour;
    private long totalWaitingCount;
    private long completedCount;
    private long canceledCount;
    private long maxWaitingTime;
    private long minWaitingTime;
    private double completedAverageWaitingTime;
    private double canceledAverageWaitingTime;
    private boolean isDeleted;

    public HourlyStatisticsDto(Long storeId, int hour, long totalWaitingCount, long completedCount,
                               long canceledCount, long maxWaitingTime, long minWaitingTime, double completedAverageWaitingTime , double canceledAverageWaitingTime, boolean isDeleted) {
        this.storeId = storeId;
        this.hour = hour;
        this.totalWaitingCount = totalWaitingCount;
        this.completedCount = completedCount;
        this.canceledCount = canceledCount;
        this.maxWaitingTime = maxWaitingTime;
        this.minWaitingTime = minWaitingTime;
        this.completedAverageWaitingTime = completedAverageWaitingTime ;
        this.canceledAverageWaitingTime = canceledAverageWaitingTime;
        this.isDeleted = isDeleted;
    }
}