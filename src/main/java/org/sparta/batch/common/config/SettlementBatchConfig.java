package org.sparta.batch.common.config;


import lombok.RequiredArgsConstructor;
import org.sparta.batch.common.resttemplate.TossPaymentsService;
import org.sparta.batch.domain.settlement.dto.SettlementDto;
import org.sparta.batch.domain.settlement.entity.Settlement;
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
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Configuration
@EnableBatchProcessing
public class SettlementBatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;

    private final SettlementService settlementService;

    private final SettlementRepository settlementRepository;
    private final SettlementSummaryRepository settlementSummaryRepository;

    private int size = 100;

    @Bean
    public Job firstJob() {
        System.out.println("first job");
        return new JobBuilder("firstJob", jobRepository)
                .start(firstStep())
                .build();
    }

    @Bean
    public Step firstStep() {
        System.out.println("first step");
        return new StepBuilder("firstStep", jobRepository)
                .<SettlementDto, Settlement> chunk(size, platformTransactionManager)
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

        System.out.println("Total items fetched: " + allData.size()); // 전체 데이터 로깅
        return new ListItemReader<>(allData);
    }

//    @Bean
//    public ItemReader<SettlementDto> beforeReader() {
//        return new RepositoryItemReaderBuilder<BeforeEntity>()
//                .name("beforeReader")
//                .pageSize(10)
//                .methodName("findAll")
//                .repository(beforeRepository)
//                .sorts(Map.of("id", Sort.Direction.ASC))
//                .build();
//    }

    @Bean
    public ItemProcessor<SettlementDto, Settlement> middleProcessor() {
        return new ItemProcessor<SettlementDto, Settlement>() {
            @Override
            public Settlement process(SettlementDto settlementDto) throws Exception {
                Settlement settlement = Settlement.builder()
                        .mId(settlementDto.getMId())
                        .paymentKey(settlementDto.getPaymentKey())
                        .transactionKey(settlementDto.getTransactionKey())
                        .orderId(settlementDto.getOrderId())
                        .build();

                return settlement;
            }
        };
    }

    @Bean
    public RepositoryItemWriter<Settlement> afterWriter() {
        return new RepositoryItemWriterBuilder<Settlement>()
                .repository(settlementRepository)
                .methodName("save")
                .build();
    }
}
