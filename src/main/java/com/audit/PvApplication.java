package com.audit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PvApplication implements CommandLineRunner {
	@Value("${api.env}")
	private String profile;

	public static void main(String[] args) {
		SpringApplication.run(PvApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Application is Running on :"+profile+ " Environment");
	} 
}
