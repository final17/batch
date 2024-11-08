package org.sparta.batch.domain.payment.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sparta.batch.domain.payment.dto.PaymentDto;
import org.sparta.batch.domain.payment.enums.Status;

import java.util.List;

import static org.sparta.batch.domain.payment.entity.QPayment.payment;
import static org.sparta.batch.domain.payment.entity.QPaymentCancel.paymentCancel;
import static org.sparta.batch.domain.payment.entity.QPaymentSuccess.paymentSuccess;
import static org.sparta.batch.domain.user.entity.QUser.user;
import static org.sparta.batch.domain.store.entity.QStore.store;

@Slf4j
@RequiredArgsConstructor
public class PaymentDslRepositoryImpl implements PaymentDslRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<PaymentDto> paymentList(String today) {
        return queryFactory
                .select(Projections.constructor(PaymentDto.class,
                        paymentSuccess.mId,
                        paymentSuccess.paymentKey,
                        payment.orderId,
                        paymentSuccess.currency,
                        paymentSuccess.method,
                        paymentCancel.totalAmount.coalesce(paymentSuccess.totalAmount),
                        Expressions.dateTemplate(String.class, "DATE_FORMAT({0} , '%Y-%m-%d')" , today),
                        new CaseBuilder()
                            .when(paymentCancel.cancels.canceledAt.isNull())
                            .then(Status.COMPLETED)
                            .otherwise(Status.CANCELLED),
                        user,
                        store))
                .from(payment)
                .innerJoin(paymentSuccess).on(paymentSuccess.orderId.eq(payment.orderId))
                .leftJoin(paymentCancel).on(paymentCancel.orderId.eq(payment.orderId))
                .innerJoin(user).on(user.id.eq(payment.user.id))
                .innerJoin(store).on(store.id.eq(payment.store.id))
                .where(todayEquals(today))
                .fetch();
    }

    private BooleanExpression todayEquals(String today) {
        return Expressions.dateTemplate(String.class, "DATE_FORMAT({0} , '%Y-%m-%d')" , paymentCancel.cancels.canceledAt.coalesce(paymentSuccess.requestedAt)).eq(today);
    }
}
