package org.sparta.batch.domain.payment.repository;

import org.sparta.batch.domain.payment.dto.PaymentDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PaymentDslRepository {
    List<PaymentDto> paymentList(String today , Pageable pageable);
}
