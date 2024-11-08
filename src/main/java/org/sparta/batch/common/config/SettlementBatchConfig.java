package org.sparta.batch.common.config;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sparta.batch.common.job.SettlementFirstStep;
import org.sparta.batch.common.job.SettlementSecondStep;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class SettlementBatchConfig {

    private final JobRepository jobRepository;
    private final SettlementFirstStep settlementFirstStep;
    private final SettlementSecondStep settlementSecondStep;

    @Bean(name = "settlementJob")
    public Job firstJob() {
        log.info("first job");
        return new JobBuilder("firstJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(settlementFirstStep.firstStep())
                .next(settlementSecondStep.secondStep())
                .build();
    }
}
