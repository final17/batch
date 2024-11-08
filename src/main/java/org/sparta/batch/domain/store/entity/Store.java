package org.sparta.batch.domain.store.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sparta.batch.common.entity.Timestamped;
import org.sparta.batch.domain.store.category.FoodCategory;
import org.sparta.batch.domain.store.category.RegionCategory;
import org.sparta.batch.domain.user.entity.User;

import java.time.LocalTime;

@Slf4j
@Getter
@Entity
@NoArgsConstructor
public class Store extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String districtCategory;

    private String image;

    @Column(nullable = false)
    private String title;

    private String description;

    private Double latitude;
    private Double longitude;

    @Column(nullable = false)
    private LocalTime openTime;

    @Column(nullable = false)
    private LocalTime closeTime;

    private Boolean isNextDay = false;

    @Column(nullable = false)
    private Long reservationTableCount;

    @Column(nullable = false)
    private Long tableCount;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private LocalTime lastOrder;

    @Column(nullable = false)
    private LocalTime turnover;

    @Column(nullable = false)
    private Long deposit;

    @Column(nullable = false)
    private Boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User user;
}
