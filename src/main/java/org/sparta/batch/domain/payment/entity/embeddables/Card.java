package org.sparta.batch.domain.payment.entity.embeddables;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Card {
    private int amount;

    private String issuerCode;

    private String acquirerCode;

    @Column(length = 20)
    private String number;

    private int installmentPlanMonths;

    @Column(length = 8)
    private String approveNo;

    private boolean useCardPoint;

    @Column(length = 10)
    private String cardType;

    @Column(length = 10)
    private String ownerType;

    @Column(length = 30)
    private String acquireStatus;

    private boolean isInterestFree;

    @Column(length = 20)
    private String interestPayer;
}
