package org.sparta.batch.domain.waiting_statistics.repository;

import org.sparta.batch.domain.waiting_statistics.entity.WaitingStatistics;

import java.util.List;

public interface WaitingStatisticsRepository {
    void saveAll(List<WaitingStatistics> waitingCounts);
}
