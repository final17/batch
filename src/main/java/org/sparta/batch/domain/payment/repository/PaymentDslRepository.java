package org.sparta.batch.domain.payment.repository;

import org.sparta.batch.domain.payment.dto.PaymentDto;

import java.util.List;

public interface PaymentDslRepository {
    List<PaymentDto> paymentList(String today);
}
