package org.sparta.batch.domain.settlement.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum SummaryType {
    DAY(Type.DAY),
    WEEK(Type.WEEK),
    MONTH(Type.MONTH);

    private final String type;

    public static SummaryType of(String type) {
        return Arrays.stream(SummaryType.values())
                .filter(r -> r.name().equalsIgnoreCase(type))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 SummaryType"));
    }

    public static class Type {
        public static final String DAY = "DAY";
        public static final String WEEK = "WEEK";
        public static final String MONTH = "MONTH";
    }
}
