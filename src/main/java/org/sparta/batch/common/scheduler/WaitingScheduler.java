package org.sparta.batch.common.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class WaitingScheduler {

    private final JobLauncher  jobLauncher;
    private final Job waitingStatisticsJob;

    @Scheduled(fixedRate = 10000L)
    //@Scheduled(cron = "0 0 0 * * ?")
    public void schedule() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLocalDate("targetDate", LocalDate.now().minusDays(1))
                .toJobParameters();

        jobLauncher.run(waitingStatisticsJob, jobParameters);
    }
}
