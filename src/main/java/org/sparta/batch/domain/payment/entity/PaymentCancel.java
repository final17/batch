package org.sparta.batch.domain.payment.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sparta.batch.common.entity.Timestamped;
import org.sparta.batch.domain.payment.entity.embeddables.Cancels;
import org.sparta.batch.domain.payment.entity.embeddables.Card;
import org.sparta.batch.domain.payment.entity.embeddables.EasyPay;
import org.sparta.batch.domain.payment.enums.PaymentMethod;
import org.sparta.batch.domain.payment.enums.PaymentStatus;
import org.sparta.batch.domain.payment.enums.PaymentType;

import java.time.OffsetDateTime;

@Getter
@Entity
@Table(name = "payment_cancel")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentCancel extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String version; // Payment 객체

    private String paymentKey; // 결제의 키 값

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private PaymentType type; // 결제 타입 정보 : NORMAL , BILLING , BRANDPAY

    @Column(nullable = false , unique = true)
    private String orderId; // 직접 만들어서 리턴해야함!

    private String mId; // 상점아이디(MID)

    private String currency; // 결제할 때 사용한 통화

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private PaymentMethod method; // 결제수단 : 카드 , 가상계좌 , 간편결제 , 휴대폰 , 계좌이체 , 문화상품권

    @Column(nullable = false)
    private Long totalAmount; // 총 결제 금액

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private PaymentStatus status; // 결제처리상태 : READY , IN_PROGRESS , WAITING_FOR_DEPOSIT , DONE , CANCELED , PARTIAL_CANCELED , ABORTED , EXPIRED

    private OffsetDateTime requestedAt; // 결제가 일어난 날짜의 시간 정보

    @Embedded
    private Card card; // 카드 정보

    @Embedded
    private EasyPay easyPay; // 간편결제 정보

    @Embedded
    private Cancels cancels;

    @Builder
    public PaymentCancel(String version,
                          String paymentKey,
                          PaymentType type,
                          String orderId,
                          String mId,
                          String currency,
                          PaymentMethod method,
                          Long totalAmount,
                          PaymentStatus status,
                          OffsetDateTime requestedAt,
                          Card card,
                          EasyPay easyPay,
                          Cancels cancels) {
        this.version = version;
        this.paymentKey = paymentKey;
        this.type = type;
        this.orderId = orderId;
        this.mId = mId;
        this.currency = currency;
        this.method = method;
        this.totalAmount = totalAmount;
        this.status = status;
        this.requestedAt = requestedAt;
        this.card = card;
        this.easyPay = easyPay;
        this.cancels = cancels;
    }
}
