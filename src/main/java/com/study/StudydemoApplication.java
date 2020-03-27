package com.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StudydemoApplication {

	public static void main(String[] args) {
		System.out.println("这是springBoot项目");
		//System.setProperty("spring.devtools.restart.enabled", "true");
		SpringApplication.run(StudydemoApplication.class, args);
	}

}
