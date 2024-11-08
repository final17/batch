package org.sparta.batch.domain.payment.entity.embeddables;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.sparta.batch.domain.payment.enums.PaymentStatus;

import java.time.OffsetDateTime;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cancels {
    private String transactionKey; // 취소 건의 키 값
    private String cancelReason;   // 취소 사유
    @Column(name = "canceled_at")
    private OffsetDateTime canceledAt; // 취소 날짜와 시간 정보
    private int cancelEasyPayDiscountAmount; // 간편결제 서비스의 포인트
    private Long cancelAmount;  // 취소 금액
    private Long taxFreeAmount; // 취소된 금액중 면세 금액
    private Long refundableAmount; // 결제 취소 후 환불 가능한 잔액
    private PaymentStatus cancelStatus;  // 취소 상태! DONE이면 성공적으로 취소된 상태
    private String receiptKey;  //취소 건의 현금영수증 키 값
    private String cancelRequestId; // 취소요청 ID! 비동기 결제에만 적용되는 특수 값
}
