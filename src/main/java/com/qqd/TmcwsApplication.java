package com.qqd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication(exclude={DruidConfig.class})
public class TmcwsApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(TmcwsApplication.class, args);
	}
}
