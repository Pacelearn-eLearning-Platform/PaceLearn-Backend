package com.charusat.pacelearn;

import com.charusat.pacelearn.config.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
@EnableConfigurationProperties({ ApplicationProperties.class })
public class PacelearnApplication {

	private static final Logger logger = LoggerFactory.getLogger(PacelearnApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(PacelearnApplication.class, args);
	}

}
