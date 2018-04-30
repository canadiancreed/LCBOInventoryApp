package com.creed.project.lcboapp.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BatchJobTestConfiguration {

    @Autowired
    private Job importCostFeed;

    @Bean
    public JobLauncherTestUtils jobLauncherTestUtils() {

        JobLauncherTestUtils testUtils = new JobLauncherTestUtils();
        testUtils.setJob(importCostFeed);

        return testUtils;
    }
}