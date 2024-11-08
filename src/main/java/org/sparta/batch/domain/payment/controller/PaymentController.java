package org.sparta.batch.domain.payment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/payment")
@RestController
public class PaymentController {

    private final JobLauncher jobLauncher;
    private final JobRepository jobRepository;
    private final Job firstJob;

    public PaymentController(JobLauncher jobLauncher , JobRepository jobRepository , @Qualifier("settlementJob") Job firstJob) {
        this.jobLauncher = jobLauncher;
        this.jobRepository = jobRepository;
        this.firstJob = firstJob;
    }

    @GetMapping
    public String payment() {
        try {
            jobLauncher.run(firstJob, new JobParameters()); // JobParameters 필요에 따라 설정
            System.out.println("Batch job executed successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "OK";
    }
}
