package org.sparta.batch.common.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.sparta.batch.domain.settlement.enums.SummaryType;
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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableScheduling
public class SettlementScheduler {
    private final JobLauncher jobLauncher;
    private final Job job1;
    private final Job job2;
    private final Job job3;

    public SettlementScheduler(JobLauncher jobLauncher ,
                               @Qualifier("settlementJob") Job job1,
                               @Qualifier("settlementSummaryWeek") Job job2,
                               @Qualifier("settlementSummaryMonth") Job job3
    ) {
        this.jobLauncher = jobLauncher;
        this.job1 = job1;
        this.job2 = job2;
        this.job3 = job3;
    }

    @Scheduled(cron = "0 0 23 * * *")
    public void runBatchJob1() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("timeStamp", System.currentTimeMillis())
                .addString("type" , SummaryType.DAY.name())
                .toJobParameters();

        jobLauncher.run(job1, jobParameters);
    }

    @Scheduled(cron = "0 0 23 * * SUN")
    public void runBatchJob2() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("timeStamp", System.currentTimeMillis())
                .addString("type" , SummaryType.WEEK.name())
                .toJobParameters();

        jobLauncher.run(job2, jobParameters);
    }

    @Scheduled(cron = "0 0 23 L-1 * *")
    public void runBatchJob3() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("timeStamp", System.currentTimeMillis())
                .addString("type" , SummaryType.MONTH.name())
                .toJobParameters();

        jobLauncher.run(job3, jobParameters);
    }
}
