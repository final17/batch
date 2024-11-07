package org.sparta.batch.domain.waiting_statistics.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class WaitingStatistics {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long storeId;
    private Integer count;
    private LocalDate date;
    private LocalTime time;

    @Builder
    public WaitingStatistics(Long storeId, Integer count, LocalDate date, LocalTime time) {
        this.storeId = storeId;
        this.count = count;
        this.date = date;
        this.time = time;
    }
}
