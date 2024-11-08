package org.sparta.batch.domain.waiting_statistics.shedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class WaitingBatch {

    private final JobLauncher jobLauncher;
    @Qualifier("waitingJob")
    private Job waitingCountJob;

//    @Scheduled(fixedRate = 1000L)
    public void runBatchJob() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        log.info("잡 실행!");
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("timeStamp", System.currentTimeMillis())
                .toJobParameters();

        jobLauncher.run(waitingCountJob, jobParameters);
    }
}
