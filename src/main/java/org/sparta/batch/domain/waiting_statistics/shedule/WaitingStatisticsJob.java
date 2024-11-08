package org.sparta.batch.domain.waiting_statistics.shedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.sparta.batch.domain.waiting_statistics.entity.WaitingStatistics;
import org.sparta.batch.domain.waiting_statistics.repository.WaitingStatisticsRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class WaitingStatisticsJob {

    private final RedissonClient redisson;
    private final WaitingStatisticsRepository waitingStatisticsRepository;

    @Bean(name = "waitingJob")
    public Job watingCountJob(JobRepository jobRepository, Step waitingCountStep) {
        return new JobBuilder("waitingJob", jobRepository)
                .start(waitingCountStep)
                .build();
    }

    @Bean
    public Step waitingCountStep(JobRepository jobRepository, Tasklet waitingCountTasklet, PlatformTransactionManager platformTransactionManager) throws Exception {
        return new StepBuilder("waitingCountStep", jobRepository)
                .tasklet(waitingCountTasklet, platformTransactionManager)
                .build();
    }

    @Bean
    public Tasklet waitingCountTasklet() {
        return ((contribution, chunkContext) -> {
            List<WaitingStatistics> waitingCounts = new ArrayList<>();
            int batchSize = 50000;
            List<String> keys = redisson.getKeys()
                    .getKeysStream()
                    .filter(x-> x.startsWith("waiting:store:")).toList();
            System.out.println("keys.size() = " + keys.size());

            long completeCount=0;
            for (var key : keys) {
                Long storeId = extractStoreIdFromKey(key);
                RAtomicLong atomicLong = redisson.getAtomicLong(key);
                int count = (int)atomicLong.get();
                LocalDate date = LocalDate.now();
                LocalTime time = LocalTime.now().withMinute(0).withSecond(0).withNano(0);
                waitingCounts.add(WaitingStatistics.builder()
                        .date(date)
                        .time(time)
                        .storeId(storeId)
                        .count(count)
                        .build());
                if (waitingCounts.size() > batchSize) {
                    log.info("insert 시작!!");
                    waitingStatisticsRepository.saveAll(new ArrayList<>(waitingCounts));
                    completeCount += waitingCounts.size();
                    log.info("insert 완료!! 처리 완료 사이즈: {}" , completeCount);
                    waitingCounts.clear(); // 리스트 초기화
                }
            }
            log.info("JOB 완료!!");
            waitingStatisticsRepository.saveAll(waitingCounts);
            return RepeatStatus.FINISHED;
        });
    }

    private Long extractStoreIdFromKey(String key) {
        String storeId = key.substring(key.lastIndexOf(":") + 1);
        return Long.parseLong(storeId);
    }
}
