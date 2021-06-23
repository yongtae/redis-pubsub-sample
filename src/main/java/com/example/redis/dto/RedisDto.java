package com.example.redis.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class RedisDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private String data = "";
	@Override
	public String toString() {
		return "RedisDto [data=" + data + "]";
	}
	
}
