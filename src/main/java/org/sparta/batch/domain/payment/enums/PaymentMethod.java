package org.sparta.batch.domain.payment.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum PaymentMethod {
    CARD(DisplayName.CARD),
    VIRTUAL_ACCOUNT(DisplayName.VIRTUAL_ACCOUNT),
    EASY_PAYMENT(DisplayName.EASY_PAYMENT),
    MOBILE(DisplayName.MOBILE),
    TRANSFER(DisplayName.TRANSFER),
    CULTURE_GIFT_CARD(DisplayName.CULTURE_GIFT_CARD),
    BOOK_GIFT_CARD(DisplayName.BOOK_GIFT_CARD),
    GAME_GIFT_CARD(DisplayName.GAME_GIFT_CARD);

    private final String displayName;

    public static PaymentMethod of(String displayName) {
        return Arrays.stream(PaymentMethod.values())
                .filter(r -> r.displayName.equalsIgnoreCase(displayName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 PaymentMethod"));
    }

    public static class DisplayName {
        public static final String CARD = "카드";
        public static final String VIRTUAL_ACCOUNT = "가상계좌";
        public static final String EASY_PAYMENT = "간편결제";
        public static final String MOBILE = "휴대폰";
        public static final String TRANSFER = "계좌이체";
        public static final String CULTURE_GIFT_CARD = "문화상품권";
        public static final String BOOK_GIFT_CARD = "도서문화상품권";
        public static final String GAME_GIFT_CARD = "게임문화상품권";
    }
}