package org.sparta.batch.common.job;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.sparta.batch.domain.settlement.dto.SettlementSummaryDto;
import org.sparta.batch.domain.settlement.entity.SettlementSummary;
import org.sparta.batch.domain.settlement.enums.SummaryType;
import org.sparta.batch.domain.settlement.repository.SettlementSummaryRepository;
import org.sparta.batch.domain.settlement.service.SettlementService;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class SettlementSummaryStep {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;

    private final SettlementService settlementService;
    private final SettlementSummaryRepository settlementSummaryRepository;

    @Bean
    public Step summaryStep() {
        log.info("second step");
        return new StepBuilder("secondStep", jobRepository)
                .tasklet(summaryTasklet() , platformTransactionManager)
                .build();
    }

    @Bean
    public Tasklet summaryTasklet() {
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                // 정산 작업 수행
                log.info("Executing settlement tasklet...");

                String type = chunkContext.getStepContext().getStepExecution().getJobParameters().getString("type");

                List<SettlementSummaryDto> settlementSummaryDtos = settlementService.getSettlementSummary(SummaryType.of(type));

                List<SettlementSummary> settlementSummaries = new ArrayList<>();
                for (SettlementSummaryDto ssd : settlementSummaryDtos) {
                    SettlementSummary settlementSummary = new SettlementSummary(ssd.getSummaryDate(), ssd.getType() , ssd.getTotalAmount() , ssd.getTotalFee() , ssd.getTotalTransactions() , ssd.getUserId() , ssd.getStoreId());
                    settlementSummaries.add(settlementSummary);
                }
                settlementSummaryRepository.saveAll(settlementSummaries);

                return RepeatStatus.FINISHED; // 작업 완료 상태 반환
            }
        };
    }
}
