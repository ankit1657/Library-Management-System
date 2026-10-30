package com.LearningSpring.LearningAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@EnableCaching
@SpringBootApplication
public class LearningApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearningApiApplication.class, args);
	}

}
