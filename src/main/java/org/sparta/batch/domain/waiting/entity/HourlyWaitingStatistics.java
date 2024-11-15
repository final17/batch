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
    @JoinColumn(name = "store_id")
    private Store store;

    private LocalDate date;
    private int hour = 0;

    @Setter
    private int totalWaitingCount = 0;
    @Setter
    private int completedCount = 0;
    @Setter
    private int canceledCount = 0;
    @Setter
    private int maxWaitingTime = 0;
    @Setter
    private int minWaitingTime = 0;
    @Setter
    private double completedAverageWaitingTime = 0.0;
    @Setter
    private double canceledAverageWaitingTime = 0.0;

    private LocalDateTime createdAt;
}