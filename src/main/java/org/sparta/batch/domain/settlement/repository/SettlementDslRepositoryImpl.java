package org.sparta.batch.domain.settlement.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sparta.batch.domain.settlement.dto.SettlementSummaryDto;
import org.sparta.batch.domain.settlement.entity.QSettlement;
import org.sparta.batch.domain.settlement.entity.QSettlementFees;
import org.sparta.batch.domain.settlement.enums.SummaryType;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class SettlementDslRepositoryImpl implements SettlementDslRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<SettlementSummaryDto> getSettlementSummary(SummaryType type, String startDate, String endDate) {
        QSettlement s = QSettlement.settlement;
        QSettlementFees sf = QSettlementFees.settlementFees;

        List<SettlementSummaryDto> results = queryFactory
                .select(
                        Projections.fields(SettlementSummaryDto.class,
                                Expressions.dateTemplate(String.class, "DATE_FORMAT({0}, '%Y-%m-%d')", s.approvedAt).as("summaryDate"),
                                s.amount.sum().as("totalAmount"),
                                sf.supplyAmount.sum().as("totalFee"),
                                s.id.countDistinct().as("totalTransactions")
                        )
                )
                .from(s)
                .innerJoin(sf).on(sf.settlement.id.eq(s.id))
                .where(Expressions.dateTemplate(String.class, "DATE_FORMAT({0}, '%Y-%m-%d')", s.approvedAt).between(startDate, endDate))
                .groupBy(Expressions.dateTemplate(String.class, "DATE_FORMAT({0}, '%Y-%m-%d')", s.approvedAt))
                .orderBy(Expressions.dateTemplate(String.class, "DATE_FORMAT({0}, '%Y-%m-%d')", s.approvedAt).asc())
                .fetch();

        for (SettlementSummaryDto dto : results) {
            dto.setType(type);
        }

        return results;
    }
}
