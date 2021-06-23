package com.example.redis;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.redis.service.GetSetService;
import com.example.redis.service.MessageService;

@Component
public class ScheduleTask {
	private static final Logger log = LoggerFactory.getLogger(ScheduleTask.class);
	
	public String oneStartChk = "0"; //실행체크
	
	@Autowired
	ChannelTopic topic;
	
    @Autowired
	private GetSetService getSetService;
    
    @Autowired
	private MessageService messageService;
    
//    @Scheduled(fixedRate = 5000)
    public void getSetSchedule() throws Exception {
    	getSetService.test();
    }
    
//	@Scheduled(fixedRate = 1000)
	public void messageSendSchedule() throws Exception {
		if(oneStartChk.equals("1")) {
			log.info("실행종료-oneStarkChk=="+oneStartChk);
			return;
		} else {
			log.info("실행시작-oneStarkChk=="+oneStartChk);
			oneStartChk="1";
		}
		
		messageService.minSendMsg("pub01","sub01");
	}
	
}
