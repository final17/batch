package org.sparta.batch.domain.payment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sparta.batch.domain.payment.dto.PaymentDto;
import org.sparta.batch.domain.payment.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public List<PaymentDto> paymentDtoList(String today) {
        return paymentRepository.paymentList(today);
    }
}
