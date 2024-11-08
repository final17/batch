package org.sparta.batch.common.config;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sparta.batch.common.job.SettlementStep;
import org.sparta.batch.common.job.SettlementSummaryStep;
import org.sparta.batch.domain.settlement.enums.SummaryType;
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
    private final SettlementStep settlementStep;
    private final SettlementSummaryStep settlementSummaryStep;

    @Bean(name = "settlementJob")
    public Job settlementJob() {
        log.info("settlement job");
        settlementSummaryStep.summaryType(SummaryType.DAY);
        return new JobBuilder("settlementJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(settlementStep.settleStep())
                .next(settlementSummaryStep.summaryStep())
                .build();
    }

    @Bean(name = "settlementSummaryWeek")
    public Job summaryWeekJob() {
        log.info("summaryWeek job");
        settlementSummaryStep.summaryType(SummaryType.WEEK);
        return new JobBuilder("summaryWeekJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(settlementSummaryStep.summaryStep())
                .build();
    }

    @Bean(name = "settlementSummaryMonth")
    public Job summaryMonthJob() {
        log.info("summaryMonth job");
        settlementSummaryStep.summaryType(SummaryType.MONTH);
        return new JobBuilder("summaryMonthJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(settlementSummaryStep.summaryStep())
                .build();
    }
}
