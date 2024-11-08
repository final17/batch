package org.sparta.batch.common.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sparta.batch.common.util.Converter;
import org.sparta.batch.domain.payment.dto.PaymentDto;
import org.sparta.batch.domain.payment.service.PaymentService;
import org.sparta.batch.domain.settlement.dto.SettlementDto;
import org.sparta.batch.domain.settlement.dto.SettlementFeesDto;
import org.sparta.batch.domain.settlement.entity.Settlement;
import org.sparta.batch.domain.settlement.entity.SettlementFees;
import org.sparta.batch.domain.settlement.repository.SettlementFeesRepository;
import org.sparta.batch.domain.settlement.repository.SettlementRepository;
import org.springframework.batch.core.Step;
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
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class SettlementStep {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;

    private final PaymentService paymentService;
    private final SettlementRepository settlementRepository;
    private final SettlementFeesRepository settlementFeesRepository;

    private final Converter converter;

    @Bean
    public Step settleStep() {
        log.info("settle step");
        return new StepBuilder("settleStep", jobRepository)
                .<PaymentDto, SettlementDto> chunk(10, platformTransactionManager)
                .reader(settleReader())
                .processor(settleProcessor())
                .writer(settleWriter())
                .build();
    }

    @Bean
    public ItemReader<PaymentDto> settleReader() {
        log.info("settleReader");
        LocalDate today = LocalDate.now();
        String todayStr = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        List<PaymentDto> paymentDtoList = paymentService.paymentDtoList(todayStr);

        log.info("Total items fetched : {}" , paymentDtoList.size()); // 전체 데이터 로깅
        return new ListItemReader<>(paymentDtoList);
    }

    @Bean
    public ItemProcessor<PaymentDto, SettlementDto> settleProcessor() {
        log.info("settleProcessor");
        return paymentDto -> {
            // SettlementDto 생성
            SettlementDto settlementDto = new SettlementDto();
            // PaymentDto의 필드에서 SettlementDto의 필드로 데이터 매핑
            settlementDto.setMId(paymentDto.getMId());
            settlementDto.setPaymentKey(paymentDto.getPaymentKey());
            settlementDto.setOrderId(paymentDto.getOrderId());
            settlementDto.setCurrency(paymentDto.getCurrency());
            settlementDto.setMethod(paymentDto.getMethod());
            settlementDto.setAmount(paymentDto.getAmount());
            settlementDto.setApprovedAt(paymentDto.getApprovedAt());
            // 정산 매출일 , 정산 지급일 계산
            LocalDate today = LocalDate.now();
            settlementDto.setSoldDate(converter.calculateSoldDate(today));
            settlementDto.setPaidOutDate(converter.calculatePaidOutDate(today));
            settlementDto.setSettlementFeesDtos(converter.getSettlementFeesDtos(paymentDto.getAmount()));
            settlementDto.setStatus(paymentDto.getStatus());
            settlementDto.setUser(paymentDto.getUser());
            settlementDto.setStore(paymentDto.getStore());

            return settlementDto; // 변환된 SettlementDto 반환
        };
    }

    @Bean
    public ItemWriter<SettlementDto> settleWriter() {
        log.info("settleWriter");
        return items -> items.forEach(settlementDto -> {
            LocalDate date = LocalDate.parse(settlementDto.getApprovedAt(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            OffsetDateTime offsetDateTime = date.atStartOfDay(ZoneOffset.UTC).toOffsetDateTime();

            Settlement settlement = Settlement.builder()
                    .mId(settlementDto.getMId())
                    .paymentKey(settlementDto.getPaymentKey())
                    .orderId(settlementDto.getOrderId())
                    .currency(settlementDto.getCurrency())
                    .method(settlementDto.getMethod())
                    .amount(settlementDto.getAmount())
                    .approvedAt(offsetDateTime)
                    .soldDate(settlementDto.getSoldDate())
                    .paidOutDate(settlementDto.getPaidOutDate())
                    .status(settlementDto.getStatus())
                    .user(settlementDto.getUser())
                    .store(settlementDto.getStore())
                    .build();

            Settlement saveSettlement = settlementRepository.save(settlement);
            settlementRepository.flush();

            List<SettlementFees> settlementFeesList = new ArrayList<>();
            for (SettlementFeesDto settlementFeesDto : settlementDto.getSettlementFeesDtos()) {
                SettlementFees settlementFees = new SettlementFees(saveSettlement , settlementFeesDto.getType() , settlementFeesDto.getSupplyAmount());
                settlementFeesList.add(settlementFees);
            }
            settlementFeesRepository.saveAll(settlementFeesList);
            settlementFeesRepository.flush();
        });
    }
}
