package org.sparta.batch.domain.payment.repository;

import org.sparta.batch.domain.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaymentRepository extends JpaRepository<Payment, Long> , PaymentDslRepository {
    @Query("SELECT p FROM Payment p INNER JOIN FETCH p.user INNER JOIN FETCH p.store WHERE p.orderId = :orderId")
    Payment findByOrderId(String orderId);
}
