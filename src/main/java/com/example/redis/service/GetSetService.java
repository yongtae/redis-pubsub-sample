package com.example.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import com.example.redis.dto.RedisDto;

@Service
public class GetSetService {
	
	@Autowired
    RedisTemplate<String, Object> redisTemplate;
    
    public void test() {
    	//get/set을 위한 객체
        ValueOperations<String, Object> vop = redisTemplate.opsForValue();
        
        //자료형 생성
        RedisDto setData = new RedisDto();
        setData.setData("sendData");
        vop.set("key", setData.toString());
        
        String getData = (String) vop.get("key");
        System.out.println(getData);//jdk
    }

}
