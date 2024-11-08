package org.sparta.batch.common.util;

import org.sparta.batch.domain.settlement.dto.SettlementFeesDto;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Converter {
    public LocalDate calculateSoldDate(LocalDate today) {
        LocalDate soldDate = today.plusDays(1);

        // 주말이면 월요일로 이동
        if (soldDate.getDayOfWeek() == DayOfWeek.SATURDAY) {
            soldDate = soldDate.plusDays(2);
        } else if (soldDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
            soldDate = soldDate.plusDays(1);
        }

        return soldDate;
    }

    public LocalDate calculatePaidOutDate(LocalDate today) {
        LocalDate paidOutDate = today;
        int daysAdded = 0;

        // 3일을 주말을 제외하고 추가
        while (daysAdded < 3) {
            paidOutDate = paidOutDate.plusDays(1);
            if (paidOutDate.getDayOfWeek() != DayOfWeek.SATURDAY && paidOutDate.getDayOfWeek() != DayOfWeek.SUNDAY) {
                daysAdded++;
            }
        }

        return paidOutDate;
    }

    public List<SettlementFeesDto> getSettlementFeesDtos(Long amount) {
        Map<String , Double> fees = new HashMap<>();
        fees.put("이용 수수료" , 3.3);
        fees.put("PG 수수료" , 3.3);

        List<SettlementFeesDto> settlementFeesDtoList = new ArrayList<>();
        for (String type : fees.keySet()) {
            SettlementFeesDto settlementFeesDto = new SettlementFeesDto();
            Long supplyAmount = (long) ((double) amount / 100 * fees.get(type));
            settlementFeesDto.setType(type);
            settlementFeesDto.setSupplyAmount(supplyAmount);
            settlementFeesDtoList.add(settlementFeesDto);
        }
        return settlementFeesDtoList;
    }
}
