package org.sparta.batch.domain.settlement.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.sparta.batch.domain.payment.enums.PaymentMethod;
import org.sparta.batch.domain.payment.enums.Status;
import org.sparta.batch.domain.store.entity.Store;
import org.sparta.batch.domain.user.entity.User;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class SettlementDto {
    private String mId;                 // 상점아이디(MID)
    private String paymentKey;          // 결제의 키 값
    private String orderId;             // 주문번호
    private String currency;            // 결제할때 사용한 통화
    private PaymentMethod method;       // 결제수단 : 카드 , 가상계좌 , 간편결제 , 휴대폰 , 계좌이체 , 문화상품권
    private Long amount;                // 결제금액
    private String approvedAt;          // 승인날짜
    private LocalDate soldDate;         // 지급 금액의 정산 기준이 되는 정산 매출일
    private LocalDate paidOutDate;      // 지급 금액을 상점에 지급할 정산 지급일
    private List<SettlementFeesDto> settlementFeesDtos;  // List<JSON [type , fee]>
    private Status status;              // 대기 , 취소 , 완료 , 실패
    private User user;                  // 유저
    private Store store;                // 상점
}
