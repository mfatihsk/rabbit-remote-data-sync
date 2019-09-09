package com.isik;

import com.isik.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * @author @fisik
 */
@SpringBootApplication
@EnableConfigurationProperties({AppProperties.class})
public class RabbitDataSyncApp {

    public static void main(String[] args) {
        SpringApplication.run(RabbitDataSyncApp.class, args);
    }

    @Bean
    @Primary
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(2);
        scheduler.setThreadNamePrefix("CommonThreadPoolTaskScheduler");
        return scheduler;
    }
}
