package org.sparta.batch.domain.settlement.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sparta.batch.domain.settlement.dto.SettlementDto;
import org.sparta.batch.domain.settlement.dto.SettlementFeesDto;
import org.sparta.batch.domain.settlement.entity.Settlement;
import org.sparta.batch.domain.settlement.entity.SettlementFees;
import org.sparta.batch.domain.settlement.enums.SummaryType;
import org.sparta.batch.domain.settlement.repository.SettlementFeesRepository;
import org.sparta.batch.domain.settlement.repository.SettlementRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class SettlementService {

    private final SettlementRepository settlementRepository;
    private final SettlementFeesRepository settlementFeesRepository;

    public List<SettlementDto> getSettlements(SummaryType summaryType) {
        LocalDate today = LocalDate.now();
        String startDt = "";
        String endDt = "";
        switch (summaryType) {
            case DAY -> {
                startDt = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                endDt = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }
            case WEEK -> {
                startDt = today.minusDays(7).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                endDt = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }
            case MONTH -> {
                startDt = today.minusDays(30).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                endDt = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }
        }

        List<Settlement> settlements = settlementRepository.getSettlement(startDt , endDt);

        return settlements.stream().map(it -> {
            SettlementDto settlementDto = new SettlementDto();
            settlementDto.setMId(it.getMId());
            settlementDto.setOrderId(it.getOrderId());
            settlementDto.setCurrency(it.getCurrency());
            settlementDto.setMethod(it.getMethod());
            settlementDto.setAmount(it.getAmount());
            settlementDto.setApprovedAt(it.getApprovedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            settlementDto.setSoldDate(it.getSoldDate());
            settlementDto.setPaidOutDate(it.getPaidOutDate());
            settlementDto.setStatus(it.getStatus());
            settlementDto.setUser(it.getUser());
            settlementDto.setStore(it.getStore());

            List<SettlementFeesDto> settlementFeesDtos = new ArrayList<>();
            List<SettlementFees> settlementFeesList = settlementFeesRepository.findBySettlementId(it.getId());
            for (SettlementFees fees : settlementFeesList) {
                SettlementFeesDto settlementFeesDto = new SettlementFeesDto();
                settlementFeesDto.setSupplyAmount(fees.getSupplyAmount());
                settlementFeesDto.setType(fees.getType());
                settlementFeesDtos.add(settlementFeesDto);
            }
            settlementDto.setSettlementFeesDtos(settlementFeesDtos);

            return settlementDto;
        }).toList();
    }
}
