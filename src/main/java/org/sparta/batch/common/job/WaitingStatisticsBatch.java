package org.sparta.batch.common.job;

import jakarta.persistence.EntityManagerFactory;
import org.sparta.batch.domain.store.entity.Store;
import org.sparta.batch.domain.waiting.entity.WaitingHistory;
import org.sparta.batch.domain.waiting.entity.statistics.*;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

@Configuration
public class WaitingStatisticsBatch {


    @Bean(name = "autoCancelRegisteredWaitingReader")
    @StepScope
    JpaPagingItemReader<WaitingHistory> autoCancelRegisteredWaitingReader(EntityManagerFactory entityManagerFactory,
                                                                   @Value("#{jobParameters['targetDate']}") LocalDate targetDate) {
        // 해당 집계일에 아직 registered상태면 모두 Canceled로 바꿔준다.
        return new JpaPagingItemReaderBuilder<WaitingHistory>()
                .name("waitingHistoryReader")
                .entityManagerFactory(entityManagerFactory)
                .parameterValues(Map.of("targetDate", targetDate))
                .queryString("""
                        SELECT w
                        FROM WaitingHistory w
                        WHERE w.status = 'REGISTERED' AND DATE(w.registeredAt) = :targetDate
                        """)
                .pageSize(100)
                .build();
    }

    @Bean(name = "autoCancelRegisteredWaitingProcessor")
    @StepScope
    public ItemProcessor<WaitingHistory, WaitingHistory> autoCancelRegisteredWaitingProcessor(@Value("#{jobParameters['targetDate']}") LocalDate targetDate) {
        return waitingHistory -> {
            // targetDate가 이전 날이니까 취소 시간은 다음날 00시 00분 00초
            waitingHistory.setCanceled(targetDate.plusDays(1).atTime(0,0,0,0));
            return waitingHistory;
        };
    }

    @StepScope
    @Bean(name = "autoCancelRegisteredWaitingWriter")
    public JpaItemWriter<WaitingHistory> autoCancelRegisteredWaitingWriter(EntityManagerFactory entityManagerFactory) {
        return new JpaItemWriterBuilder<WaitingHistory>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean(name = "autoCancelRegisteredWaitingStep")
    public Step autoCancelRegisteredWaitingStep(JobRepository jobRepository,
                                   JpaPagingItemReader<WaitingHistory> autoCancelRegisteredWaitingReader,
                                   ItemProcessor<WaitingHistory, WaitingHistory> autoCancelRegisteredWaitingProcessor,
                                   JpaItemWriter<WaitingHistory> autoCancelRegisteredWaitingWriter,
                                   @Qualifier("dataTransactionManager") PlatformTransactionManager transactionManager) {
        return new StepBuilder("autoCancelRegisteredWaitingStep", jobRepository)
                .<WaitingHistory, WaitingHistory>chunk(100, transactionManager)
                .reader(autoCancelRegisteredWaitingReader)
                .processor(autoCancelRegisteredWaitingProcessor)
                .writer(autoCancelRegisteredWaitingWriter)
                .build();
    }

    @Bean(name = "hourlyWaitingStatisticsReader")
    @StepScope
    public JpaPagingItemReader<HourlyStatisticsDto> hourlyWaitingStatisticsReader(EntityManagerFactory entityManagerFactory,
                                                                                  @Value("#{jobParameters[targetDate]}") LocalDate targetDate) {
        return new JpaPagingItemReaderBuilder<HourlyStatisticsDto>()
                .name("hourlyWaitingStatisticsReader")
                .entityManagerFactory(entityManagerFactory)
                .parameterValues(Map.of("targetDate", targetDate))
                .queryString("""
                        SELECT new org.sparta.batch.domain.waiting.entity.statistics.HourlyStatisticsDto(
                            w.store.id,
                            HOUR(w.registeredAt),
                            COUNT(CASE WHEN w.status IN ('COMPLETED', 'CANCELED') THEN 1 ELSE NULL END),
                            COUNT(CASE WHEN w.status = 'COMPLETED' THEN 1 ELSE NULL END),
                            COUNT(CASE WHEN w.status = 'CANCELED' THEN 1 ELSE NULL END),
                            COALESCE(MAX(w.waitingTime), 0),
                            COALESCE(MIN(w.waitingTime), 0),
                            COALESCE(AVG(CASE WHEN w.status = 'COMPLETED' THEN w.waitingTime ELSE NULL END), 0),
                            w.store.isDeleted
                        )
                        FROM WaitingHistory w
                        INNER JOIN w.store
                        WHERE DATE(w.registeredAt) = :targetDate AND w.store.isDeleted = false
                        GROUP BY w.store.id, HOUR(w.registeredAt)
                        ORDER BY HOUR(w.registeredAt)
                        """)
                .pageSize(100)
                .build();
    }

    @Bean(name = "hourlyStatisticsProcessor")
    @StepScope
    public ItemProcessor<HourlyStatisticsDto, HourlyWaitingStatistics> hourlyStatisticsProcessor() {
        return item -> {
            Long storeId = item.getStoreId();
            int hour = item.getHour();
            int totalWaitingCount = (int) item.getTotalWaitingCount();
            int completedCount = (int) item.getCompletedCount();
            int canceledCount = (int)item.getCanceledCount();
            int maxWaitingTime = (int) item.getMaxWaitingTime();
            int minWaitingTime = (int) item.getMinWaitingTime();
            double averageWaitingTime = item.getAverageWaitingTime();

            return HourlyWaitingStatistics.builder()
                    .store(new Store(storeId))
                    .hour(hour)
                    .totalWaitingCount(totalWaitingCount)
                    .completedCount(completedCount)
                    .canceledCount(canceledCount)
                    .maxWaitingTime(maxWaitingTime)
                    .minWaitingTime(minWaitingTime)
                    .averageWaitingTime(averageWaitingTime)
                    .createdAt(LocalDateTime.now())
                    .date(LocalDate.now().minusDays(1))
                    .build();
        };
    }

    @Bean(name = "hourlyStatisticsWriter")
    @StepScope
    public JpaItemWriter<HourlyWaitingStatistics> hourlyStatisticsWriter(EntityManagerFactory entityManagerFactory) {
        return new JpaItemWriterBuilder<HourlyWaitingStatistics>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean(name = "hourlyWaitingStatisticsStep")
    @JobScope
    public Step hourlyWaitingStatisticsStep(JobRepository jobRepository,
                                            JpaPagingItemReader<HourlyStatisticsDto> hourlyWaitingStatisticsReader,
                                            ItemProcessor<HourlyStatisticsDto, HourlyWaitingStatistics> hourlyStatisticsProcessor,
                                            JpaItemWriter<HourlyWaitingStatistics> hourlyStatisticsWriter,
                                            @Qualifier("dataTransactionManager") PlatformTransactionManager transactionManager) {
        return new StepBuilder("hourlyWaitingStatisticsStep", jobRepository)
                .<HourlyStatisticsDto, HourlyWaitingStatistics>chunk(100, transactionManager)
                .reader(hourlyWaitingStatisticsReader)
                .processor(hourlyStatisticsProcessor)
                .writer(hourlyStatisticsWriter)
                .build();
    }

    @Bean(name = "dailyWaitingStatisticsReader")
    @StepScope
    public JpaPagingItemReader<DailyWaitingStatisticsDto> dailyWaitingStatisticsReader(EntityManagerFactory entityManagerFactory,
                                                                                       @Value("#{jobParameters[targetDate]}") LocalDate targetDate) {
        return new JpaPagingItemReaderBuilder<DailyWaitingStatisticsDto>()
                .name("dailyWaitingStatisticsReader")
                .entityManagerFactory(entityManagerFactory)
                .parameterValues(Map.of("targetDate", targetDate))
                .queryString("""
                        SELECT new org.sparta.batch.domain.waiting.entity.statistics.DailyWaitingStatisticsDto(
                            w.store.id,
                            :targetDate,
                             COUNT(CASE WHEN w.status IN ('COMPLETED', 'CANCELED') THEN 1 ELSE NULL END),
                             COUNT(CASE WHEN w.status = 'COMPLETED' THEN 1 ELSE NULL END),
                             COUNT(CASE WHEN w.status = 'CANCELED' THEN 1 ELSE NULL END),
                            COALESCE(AVG(CASE WHEN w.status = 'COMPLETED' THEN w.waitingTime ELSE NULL END), 0),
                            w.store.isDeleted
                        )
                        FROM WaitingHistory w
                        INNER JOIN w.store
                        WHERE DATE(w.registeredAt) = :targetDate AND w.store.isDeleted = false
                        GROUP BY w.store.id
                        """)
                .pageSize(100)
                .build();
    }

    @Bean(name = "dailyStatisticsProcessor")
    @StepScope
    public ItemProcessor<DailyWaitingStatisticsDto, DailyWaitingStatistics> dailyStatisticsProcessor() {
        return DailyWaitingStatistics::new;
    }

    @Bean(name = "dailyStatisticsWriter")
    @StepScope
    public JpaItemWriter<DailyWaitingStatistics> dailyStatisticsWriter(EntityManagerFactory entityManagerFactory) {
        return new JpaItemWriterBuilder<DailyWaitingStatistics>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    @JobScope
    public Step dailyWaitingStatisticsStep(JobRepository jobRepository,
                                           JpaPagingItemReader<DailyWaitingStatisticsDto> dailyWaitingStatisticsReader,
                                           ItemProcessor<DailyWaitingStatisticsDto, DailyWaitingStatistics> dailyStatisticsProcessor,
                                           JpaItemWriter<DailyWaitingStatistics> dailyStatisticsWriter,
                                           @Qualifier("dataTransactionManager") PlatformTransactionManager transactionManager) {
        return new StepBuilder("dailyWaitingStatisticsStep", jobRepository)
                .<DailyWaitingStatisticsDto, DailyWaitingStatistics>chunk(100, transactionManager)
                .reader(dailyWaitingStatisticsReader)
                .processor(dailyStatisticsProcessor)
                .writer(dailyStatisticsWriter)
                .build();
    }

    @Bean
    public Job waitingStatisticsJob(JobRepository jobRepository,
                                      Step autoCancelRegisteredWaitingStep,
                                      Step hourlyWaitingStatisticsStep,
                                      Step dailyWaitingStatisticsStep) {
        return new JobBuilder("waitingStatisticsJob", jobRepository)
                .start(autoCancelRegisteredWaitingStep)
                .next(hourlyWaitingStatisticsStep)
                .next(dailyWaitingStatisticsStep)
                .build();
    }
}
