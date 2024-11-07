package org.sparta.batch.domain.payment.entity;

import jakarta.persistence.*;
import lombok.*;
import org.sparta.batch.common.entity.Timestamped;
import org.sparta.batch.domain.payment.enums.Status;
import org.sparta.batch.domain.store.entity.Store;
import org.sparta.batch.domain.user.entity.User;

@Builder
@Getter
@Entity
@Table(name = "payment")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false , unique = true)
    private String orderId; // 주문 번호

    @Column(nullable = false)
    private String orderName;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Status status; // 준비 , 완료 , 취소

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    public void updateStatus(Status status) {
        this.status = status;
    }
}
