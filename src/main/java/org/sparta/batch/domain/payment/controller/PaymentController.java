package org.sparta.batch.domain.payment.controller;

import lombok.RequiredArgsConstructor;
import org.sparta.batch.domain.payment.service.PaymentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/payment")
@RestController
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping
    public String payment() {
        return paymentService.test();
    }
}
