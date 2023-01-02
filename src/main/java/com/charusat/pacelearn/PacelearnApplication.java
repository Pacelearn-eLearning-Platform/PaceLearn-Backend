package com.charusat.pacelearn;

import com.charusat.pacelearn.config.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ ApplicationProperties.class })
public class PacelearnApplication {

	public static void main(String[] args) {
		SpringApplication.run(PacelearnApplication.class, args);
	}

}
