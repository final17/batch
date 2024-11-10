package org.sparta.batch.domain.waiting.entity;

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

    private LocalDate date;
    private int hour;

    @Setter
    private int totalWaitingCount;
    @Setter
    private int completedCount;
    @Setter
    private int canceledCount;
    @Setter
    private int maxWaitingTime;
    @Setter
    private int minWaitingTime;
    @Setter
    private double averageWaitingTime;

    private LocalDateTime createdAt;
}