package org.sparta.batch.domain.waiting.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Getter
@NoArgsConstructor
public class DailyWaitingStatisticsDto {

    private Long storeId;
    private Date date;
    private long totalWaitingCount;
    private long completedCount;
    private long canceledCount;
    private double completedAverageWaitingTime;
    private double canceledAverageWaitTime;
    private boolean isDeleted;

    public DailyWaitingStatisticsDto(Long storeId, Date date, long totalWaitingCount, long completedCount, long canceledCount, double averageWaitingTimeForCompleted, double averageWaitTimeForCanceled, boolean isDeleted) {
        this.storeId = storeId;
        this.date = date;
        this.totalWaitingCount = totalWaitingCount;
        this.completedCount = completedCount;
        this.canceledCount = canceledCount;
        this.completedAverageWaitingTime = averageWaitingTimeForCompleted;
        this.canceledAverageWaitTime = averageWaitTimeForCanceled;
        this.isDeleted = isDeleted;
    }
}