package org.sparta.batch.domain.payment.repository;

import org.sparta.batch.domain.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
