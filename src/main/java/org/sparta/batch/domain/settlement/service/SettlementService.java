package org.sparta.batch.domain.settlement.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sparta.batch.domain.settlement.dto.SettlementSummaryDto;
import org.sparta.batch.domain.settlement.enums.SummaryType;
import org.sparta.batch.domain.settlement.repository.SettlementRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class SettlementService {

    private final SettlementRepository settlementRepository;

    public List<SettlementSummaryDto> getSettlementSummary(SummaryType summaryType) {
        String[] dt = getDt(summaryType);
        return settlementRepository.getSettlementSummary(summaryType , dt[0] ,dt[1]);
    }

    private String[] getDt(SummaryType summaryType) {
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
        return new String[]{startDt, endDt};
    }
}
