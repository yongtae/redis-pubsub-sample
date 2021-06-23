package com.example.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.example.redis.service.GetSetService;

@EnableScheduling
@SpringBootApplication
public class RedisApplication {
	
	@Autowired
	private GetSetService getSetService;
	
	
	public RedisApplication(GetSetService getSetService) {
		super();
//		this.getSetService = getSetService;
//		getSetService.test();
	}


	public static void main(String[] args) {
		SpringApplication.run(RedisApplication.class, args);
	}

}
