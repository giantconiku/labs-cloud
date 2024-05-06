package com.giant.springbootrestapipostgresql;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootRestApiPostgreSqlApplication {

	private static final Logger logger = LogManager.getLogger(SpringBootRestApiPostgreSqlApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRestApiPostgreSqlApplication.class, args);
		logger.info("Hello Universe, I am Log4j-2 Logging Framework !");
	}
}