package org.sparta.batch.domain.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sparta.batch.domain.payment.enums.PaymentMethod;
import org.sparta.batch.domain.payment.enums.Status;
import org.sparta.batch.domain.store.entity.Store;
import org.sparta.batch.domain.user.entity.User;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PaymentDto {
    private String mId;
    private String paymentKey; // 결제의 키 값
    private String orderId;     // 주문번호
    private String currency;    // 결제할때 사용한 통화
    private PaymentMethod method; // 결제수단 : 카드 , 가상계좌 , 간편결제 , 휴대폰 , 계좌이체 , 문화상품권
    private Long amount;    // 결제 금액
    private String approvedAt;    // 결제 금액
    private Status status;  // 대기 , 취소 , 완료 , 실패
    private User user;
    private Store store;
}
