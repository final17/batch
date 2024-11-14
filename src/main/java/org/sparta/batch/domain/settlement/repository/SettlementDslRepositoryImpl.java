package org.sparta.batch.domain.settlement.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sparta.batch.domain.settlement.dto.SettlementSummaryDto;
import org.sparta.batch.domain.settlement.entity.QSettlement;
import org.sparta.batch.domain.settlement.entity.QSettlementFees;
import org.sparta.batch.domain.settlement.enums.SummaryType;
import org.sparta.batch.domain.store.entity.QStore;
import org.sparta.batch.domain.user.entity.QUser;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class SettlementDslRepositoryImpl implements SettlementDslRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<SettlementSummaryDto> getSettlementSummary(SummaryType type, String startDate, String endDate) {
        QSettlement s = QSettlement.settlement;
        QUser user = QUser.user;
        QStore store = QStore.store;

        String template = switch (type) {
            case DAY -> "DATE_FORMAT({0}, '%Y-%m-%d')";
            case MONTH -> "DATE_FORMAT({0}, '%Y-%m')";
            case WEEK -> "CONCAT(YEAR({0}), '-', WEEK({0}))";
            default -> throw new IllegalArgumentException("Invalid SummaryType: " + type);
        };

        QSettlement ss = QSettlement.settlement;
        QSettlementFees sf = QSettlementFees.settlementFees;
        JPQLQuery<Long> subquery = JPAExpressions
                .select(sf.supplyAmount.sum())
                .from(ss)
                .innerJoin(sf).on(sf.settlement.id.eq(ss.id))
                .where(Expressions.dateTemplate(String.class, "DATE_FORMAT({0}, '%Y-%m-%d')", ss.approvedAt).between(startDate, endDate));

        List<SettlementSummaryDto> results = queryFactory
                .select(
                        Projections.constructor(SettlementSummaryDto.class,
                                type == SummaryType.WEEK
                                        ? Expressions.stringTemplate(template, s.approvedAt)
                                        : Expressions.dateTemplate(String.class, template, s.approvedAt),
                                s.amount.sum().as("totalAmount"),
                                subquery,
                                s.id.count().as("totalTransactions"),
                                s.user.id.max().as("userId"),
                                s.store.id.max().as("storeId")
                        )
                )
                .from(s)
                .innerJoin(user).on(user.id.eq(s.user.id))
                .innerJoin(store).on(store.id.eq(s.store.id))
                .where(Expressions.dateTemplate(String.class, "DATE_FORMAT({0}, '%Y-%m-%d')", s.approvedAt).between(startDate, endDate))
                .groupBy(type == SummaryType.WEEK
                        ? Expressions.stringTemplate(template, s.approvedAt)
                        : Expressions.dateTemplate(String.class, template, s.approvedAt))
                .orderBy(type == SummaryType.WEEK
                        ? Expressions.stringTemplate(template, s.approvedAt).asc()
                        : Expressions.dateTemplate(String.class, template, s.approvedAt).asc())
                .fetch();

        for (SettlementSummaryDto dto : results) {
            dto.setType(type);
        }

        return results;
    }
}
