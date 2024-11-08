package org.sparta.batch.common.config;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sparta.batch.domain.settlement.dto.SettlementDto;
import org.sparta.batch.domain.settlement.dto.SettlementFeesDto;
import org.sparta.batch.domain.settlement.entity.Settlement;
import org.sparta.batch.domain.settlement.entity.SettlementFees;
import org.sparta.batch.domain.settlement.repository.SettlementFeesRepository;
import org.sparta.batch.domain.settlement.repository.SettlementRepository;
import org.sparta.batch.domain.settlement.repository.SettlementSummaryRepository;
import org.sparta.batch.domain.settlement.service.SettlementService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class SettlementBatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;

    private final SettlementService settlementService;

    private final SettlementRepository settlementRepository;
    private final SettlementFeesRepository settlementFeesRepository;
    private final SettlementSummaryRepository settlementSummaryRepository;

    private int size = 100;

    @Bean(name = "settlementJob")
    public Job firstJob() {
        log.info("first job");
        return new JobBuilder("firstJob", jobRepository)
                .start(firstStep())
                .build();
    }

    @Bean
    public Step firstStep() {
        log.info("first step");
        return new StepBuilder("firstStep", jobRepository)
                .<SettlementDto, SettlementDto> chunk(size, platformTransactionManager)
                .reader(beforeReader())
                .processor(middleProcessor())
                .writer(afterWriter())
                .build();
    }

    @Bean
    public ItemReader<SettlementDto> beforeReader() {
        List<SettlementDto> allData = new ArrayList<>();
        int page = 1;

        LocalDate today = LocalDate.now();
        String todayStr = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        log.info("today : {}" , todayStr);
        boolean hasMoreData = true;

        while (hasMoreData) {
            // API 호출하여 페이지 단위로 데이터 가져오기
            List<SettlementDto> pageData = settlementService.getSettlements(todayStr , todayStr , page , size);

            if (pageData == null || pageData.isEmpty()) {
                hasMoreData = false; // 더 이상 데이터가 없으면 루프 중단
            } else {
                allData.addAll(pageData); // 가져온 데이터를 전체 리스트에 추가
                page++; // 다음 페이지로 이동
            }
        }

        log.info("Total items fetched : {}" , allData.size()); // 전체 데이터 로깅
        return new ListItemReader<>(allData);
    }

    @Bean
    public ItemProcessor<SettlementDto, SettlementDto> middleProcessor() {
        log.info("middleProcessor");
        return SettlementDto -> SettlementDto;
    }

    @Bean
    public ItemWriter<SettlementDto> afterWriter() {
        log.info("afterWriter");
        return items -> {
            for (SettlementDto settlementDto : items) {
                // Settlement 객체 생성
                Settlement settlement = Settlement.builder()
                        .mId(settlementDto.getMId())
                        .paymentKey(settlementDto.getPaymentKey())
                        .transactionKey(settlementDto.getTransactionKey())
                        .orderId(settlementDto.getOrderId())
                        .build();

                // Settlement 저장
                Settlement saveSettlement = settlementRepository.save(settlement);

                // SettlementFees 저장
                for (SettlementFeesDto feesDto : settlementDto.getSettlementFeesDtos()) {
                    SettlementFees settlementFees = new SettlementFees(saveSettlement , feesDto.getType() , feesDto.getSupplyAmount());
                    settlementFeesRepository.save(settlementFees);
                }
            }
        };
    }
}
