package org.sparta.batch.domain.payment.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum PaymentStatus {
    READY(Status.READY),
    IN_PROGRESS(Status.IN_PROGRESS),
    WAITING_FOR_DEPOSIT(Status.WAITING_FOR_DEPOSIT),
    DONE(Status.DONE),
    CANCELED(Status.CANCELED),
    PARTIAL_CANCELED(Status.PARTIAL_CANCELED),
    ABORTED(Status.ABORTED),
    EXPIRED(Status.EXPIRED);

    private final String status;

    public static PaymentStatus of(String status) {
        return Arrays.stream(PaymentStatus.values())
                .filter(r -> r.name().equalsIgnoreCase(status))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 PaymentStatus"));
    }

    public static class Status {
        public static final String READY = "READY";
        public static final String IN_PROGRESS = "IN_PROGRESS";
        public static final String WAITING_FOR_DEPOSIT = "WAITING_FOR_DEPOSIT";
        public static final String DONE = "DONE";
        public static final String CANCELED = "CANCELED";
        public static final String PARTIAL_CANCELED = "PARTIAL_CANCELED";
        public static final String ABORTED = "ABORTED";
        public static final String EXPIRED = "EXPIRED";
    }
}
