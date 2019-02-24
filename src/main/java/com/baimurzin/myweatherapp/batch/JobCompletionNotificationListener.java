package com.baimurzin.myweatherapp.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * The listener used for listen the end of #CITY_REGISTRY table population job
 * {@link CityRegistryBatchConfiguration}
 * <p></p>
 *
 * @author Vladislav Baimurzin
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private final JdbcTemplate jdbcTemplate;

    /**
     * The map to store job id and job start time
     */
    private Map<Long, Long> jobRegister = new HashMap<>();

    @Override
    public void beforeJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.STARTED) {
            log.info("Job {} starting...", jobExecution.getJobId());
            jobRegister.put(jobExecution.getJobId(), System.nanoTime());
        }

    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED");
            long startTime = jobRegister.get(jobExecution.getJobId());
            log.info("Job#{} Execution time= {}", jobExecution.getJobId(),
                    (System.nanoTime()-startTime)/1_000_000);
            int result = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM CITY_REGISTRY", Integer.class);
            log.info("!!! CITIES FOUND IN DATABASE: {}", result);
        }
    }
}
