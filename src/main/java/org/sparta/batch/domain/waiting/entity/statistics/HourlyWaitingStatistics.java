package org.sparta.batch.domain.waiting.entity.statistics;

import jakarta.persistence.*;
import lombok.*;
import org.sparta.batch.domain.store.entity.Store;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class HourlyWaitingStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Store store;

    private LocalDate date; // 통계 날짜
    private int hour; // 시간대 (0~23)

    @Setter
    private int totalWaitingCount; // 해당 시간대 총 대기 인원 수
    @Setter
    private int completedCount; // 완료된 대기 건수
    @Setter
    private int canceledCount; // 취소된 대기 건수
    @Setter
    private int maxWaitingTime; // 최대 대기 시간 (분 단위)
    @Setter
    private int minWaitingTime; // 최소 대기 시간 (분 단위)
    @Setter
    private double averageWaitingTime; // 평균 대기 시간

    private LocalDateTime createdAt;
}