package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import util.CountScheduler;
/*
 * Scheduling : 정기적인 실행을 하도록 설정
 */
@Configuration
@EnableScheduling //스케줄링 적용
public class BatchConfig {
	@Bean
	public CountScheduler countScheduler() {
		return new CountScheduler();
	}
}
