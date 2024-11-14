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
import org.springframework.batch.item.ExecutionContext;
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
                log.info("Executing settlement tasklet...");

                // JobParameters에서 요약 유형 가져오기
                String type = chunkContext.getStepContext().getStepExecution().getJobParameters().getString("type");
                ExecutionContext executionContext = chunkContext.getStepContext().getStepExecution().getExecutionContext();

                // 마지막 처리된 인덱스 가져오기
                int lastProcessedIndex = executionContext.containsKey("lastProcessedIndex") ?
                        executionContext.getInt("lastProcessedIndex") : 0;

                // 정산 데이터 가져오기
                List<SettlementSummaryDto> settlementSummaryDtos = settlementService.getSettlementSummary(SummaryType.of(type));
                if (lastProcessedIndex >= settlementSummaryDtos.size()) {
                    // 더 이상 처리할 데이터가 없는 경우 종료
                    return RepeatStatus.FINISHED;
                }

                // 남은 데이터 처리
                for (int i = lastProcessedIndex; i < settlementSummaryDtos.size(); i++) {
                    SettlementSummaryDto ssd = settlementSummaryDtos.get(i);
                    SettlementSummary settlementSummary = new SettlementSummary(
                            ssd.getSummaryDate(), ssd.getType(), ssd.getTotalAmount(),
                            ssd.getTotalFee(), ssd.getTotalTransactions(), ssd.getUserId(), ssd.getStoreId()
                    );
                    settlementSummaryRepository.save(settlementSummary);

                    // 마지막 처리된 인덱스 업데이트 및 저장
                    executionContext.putInt("lastProcessedIndex", i + 1);
                }

                return RepeatStatus.FINISHED; // 작업 완료 상태 반환
            }
        };
    }

}
