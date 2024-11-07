package org.sparta.batch.domain.waiting_statistics.repository;

import lombok.RequiredArgsConstructor;
import org.sparta.batch.domain.waiting_statistics.entity.WaitingStatistics;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class WaitingStatisticsRepositoryImpl implements WaitingStatisticsRepository {
    private final JdbcTemplate dataJdbcTemplate;

    @Override
    public void saveAll(List<WaitingStatistics> waitingCounts) {
        String sql = "INSERT INTO waiting_statistics (store_id, date, time, count) VALUES (?, ?, ?, ?)";
        dataJdbcTemplate.batchUpdate(sql, waitingCounts, waitingCounts.size(),
                (PreparedStatement ps, WaitingStatistics waitingCount) -> {
                    ps.setLong(1, waitingCount.getStoreId());
                    ps.setDate(2, Date.valueOf(waitingCount.getDate())); // LocalDate -> java.sql.Date
                    ps.setTime(3, Time.valueOf(waitingCount.getTime())); // LocalTime -> java.sql.Time
                    ps.setInt(4, waitingCount.getCount());
                });
    }
}
