package com.qqd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude={DruidConfig.class})
public class TmcwsApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(TmcwsApplication.class, args);
	}
}
