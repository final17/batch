package org.sparta.batch.domain.payment.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum PaymentType {
    NORMAL(Type.NORMAL),
    BILLING(Type.BILLING),
    BRANDPAY(Type.BRANDPAY);

    private final String status;

    public static PaymentType of(String status) {
        return Arrays.stream(PaymentType.values())
                .filter(r -> r.name().equalsIgnoreCase(status))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 PaymentType"));
    }

    public static class Type {
        public static final String NORMAL = "NORMAL";
        public static final String BILLING = "BILLING";
        public static final String BRANDPAY = "BRANDPAY";
    }
}
