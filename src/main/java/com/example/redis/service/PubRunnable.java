package com.example.redis.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PubRunnable implements Runnable {
	private static final Logger log = LoggerFactory.getLogger(PubRunnable.class);
	
	private String pubNum;
	private String subNum;
	private MessageService messageService;
//	@Autowired
//	private MessageService messageService;
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		log.info("---thr_"+pubNum+" pubSend02.json 실행");
//		messageService.minSendMsg("pub01","sub01");
		messageService.minSendMsg(pubNum,subNum);
	}


	public PubRunnable(String pubNum, String subNum, MessageService messageService) {
		super();
		this.pubNum = pubNum;
		this.subNum = subNum;
		this.messageService = messageService;
	}

}
