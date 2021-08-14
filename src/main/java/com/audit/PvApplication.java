package com.audit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SpringBootApplication
public class PvApplication implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(PvApplication.class);
	
	@Value("${api.env}")
	private String profile;

	public static void main(String[] args) {
		SpringApplication.run(PvApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		logger.debug("Application is Running on :" + profile + " Environment");
	}
}
