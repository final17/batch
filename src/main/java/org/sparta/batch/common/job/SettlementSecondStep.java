package org.sparta.batch.common.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sparta.batch.domain.settlement.dto.SettlementDto;
import org.sparta.batch.domain.settlement.dto.SettlementFeesDto;
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
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class SettlementSecondStep {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;

    private final SettlementService settlementService;
    private final SettlementSummaryRepository settlementSummaryRepository;

    private final SummaryType summaryType = SummaryType.DAY;

    @Bean
    public Step secondStep() {
        log.info("second step");
        return new StepBuilder("secondStep", jobRepository)
                .tasklet(secondTasklet() , platformTransactionManager)
                .build();
    }

    @Bean
    public Tasklet secondTasklet() {
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                // 정산 작업 수행
                log.info("Executing settlement tasklet...");

                List<SettlementDto> settlementDtos = settlementService.getSettlements(summaryType);

                List<SettlementSummaryDto> settlementSummaryDtos = new ArrayList<>();
                String previousApprovedAt = null; // 이전 승인 날짜
                SettlementSummaryDto settlementSummaryDto = null;

                for (SettlementDto settlementDto : settlementDtos) {
                    String currentApprovedAt = settlementDto.getApprovedAt(); // 현재 승인 날짜

                    // 새로운 승인 날짜인 경우
                    if (previousApprovedAt == null || !previousApprovedAt.equals(currentApprovedAt)) {
                        // 이전 집계가 존재하면 리스트에 추가
                        if (settlementSummaryDto != null) {
                            settlementSummaryDtos.add(settlementSummaryDto);
                        }

                        // 새 집계 객체 초기화
                        settlementSummaryDto = new SettlementSummaryDto();
                        settlementSummaryDto.setSummaryDate(LocalDate.parse(currentApprovedAt));
                        settlementSummaryDto.setType(summaryType);
                        settlementSummaryDto.setTotalAmount(settlementDto.getAmount()); // 첫 번째 항목의 금액으로 초기화
                        settlementSummaryDto.setTotalTransactions(1L); // 첫 번째 거래
                        settlementSummaryDto.setTotalFee(calculateTotalFees(settlementDto.getSettlementFeesDtos())); // 첫 번째 항목의 수수료
                    } else {
                        // 동일한 날짜인 경우 집계 업데이트
                        settlementSummaryDto.setTotalAmount(settlementSummaryDto.getTotalAmount() + settlementDto.getAmount());
                        settlementSummaryDto.setTotalTransactions(settlementSummaryDto.getTotalTransactions() + 1);
                        settlementSummaryDto.setTotalFee(settlementSummaryDto.getTotalFee() + calculateTotalFees(settlementDto.getSettlementFeesDtos()));
                    }

                    // 이전 승인 날짜 업데이트
                    previousApprovedAt = currentApprovedAt;
                }

                // 마지막 집계 추가
                if (settlementSummaryDto != null) {
                    settlementSummaryDtos.add(settlementSummaryDto);
                }

                List<SettlementSummary> settlementSummaries = new ArrayList<>();
                for (SettlementSummaryDto ssd : settlementSummaryDtos) {
                    SettlementSummary settlementSummary = new SettlementSummary(ssd.getSummaryDate() , ssd.getType() , ssd.getTotalAmount() , ssd.getTotalFee() , ssd.getTotalTransactions());
                    settlementSummaries.add(settlementSummary);
                }
                settlementSummaryRepository.saveAll(settlementSummaries);

                return RepeatStatus.FINISHED; // 작업 완료 상태 반환
            }
        };
    }

    // 수수료 총합 계산 메서드
    private Long calculateTotalFees(List<SettlementFeesDto> settlementFeesDtos) {
        return settlementFeesDtos.stream()
                .mapToLong(SettlementFeesDto::getSupplyAmount)
                .sum();
    }
}
