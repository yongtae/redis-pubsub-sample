package com.example.redis.service;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
public class MessageService implements MessageListener {
	private static final Logger log = LoggerFactory.getLogger(MessageService.class);
	
	@Autowired
    RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
	ChannelTopic topic;
	
	public static List<String> messageList = new ArrayList<String>();

	@Override
	public void onMessage(Message message, byte[] pattern) {
		// TODO Auto-generated method stub
		messageList.add(message.toString());
//        System.out.println("Message received: " + message.toString());
        log.info("Message received: " + message.toString());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	    Date resDate = new Date();
        String resMsg = message.toString().replace("응답시간", sdf.format(resDate));
        this.fileSave(resMsg,"redis_sub01.log"); //응답메세지 파일저장
	}

	public void sendMessage(String msg,String fileName) {
		redisTemplate.convertAndSend(topic.getTopic(), msg);
//		this.fileSave(msg,"redis_pub.log"); //전송메세지 파일저장
		this.fileSave(msg,"redis_"+fileName+".log"); //전송메세지 파일저장
    }
	
//	1분동안 메세지 전송
	public void minSendMsg(String publishName, String subscriberName) {
//		this.fileDelete("redis_pub.log"); //파일 초기화(삭제처리)
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	    Date startDate = new Date();
	    
	    Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
//		cal.add(Calendar.SECOND, 1);
		cal.add(Calendar.MINUTE, 1);
	    Date endDate = cal.getTime();
//	    
//	    System.out.println(sdf.format(startDate));
//	    System.out.println(sdf.format(endDate));

//	    if(endDate.after(startDate)){
//	        System.out.println("endDate is after startDate");
//	    }
	    
		log.info("--전송시작--");
	    this.sendMessage("--전송시작--",publishName);
		while(true) {
			Date loopDate = new Date();

			if(loopDate.after(endDate)){
				log.info("--전송종료--");
				this.sendMessage("--전송종료--",publishName);
				break;
		    }
			//		메세지 형식 - 채널명_퍼블리시명_구독자명_전송시간_응답시간_기타문자
			String msg = "";
			msg += topic.getTopic();
			msg += "_"+publishName+"_"+subscriberName;
			msg += "_"+sdf.format(loopDate); //전송시간
			msg += "_응답시간"; //전송시간
//			msg += "_기타메세지~~~";
//			약 256byte
//			msg += "_기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~";
//			512byte
//			msg += "_기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~_기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~";
//			1024byte
//			msg += "_기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~_기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~";
//			2048byte
			msg += "_기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~_기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~기타메세지~~~";

//			log.info("--전송메세지--:"+msg);
			this.sendMessage(msg,publishName);
		}
		
	}
	
	//	파일쓰기
	public void fileSave(String msg, String fileName) {
		ClassPathResource classPathResource = new ClassPathResource("file/"+fileName);
		ClassPathResource classPathResource2 = new ClassPathResource("file");
		
		try {
//			classPathResource2.getURI();
		    boolean exists = classPathResource.exists();
		    
		    File file;
		    if (!exists) {
		        file = new File(classPathResource2.getURI().getPath()+"/"+fileName);
		    } else {
		    	file = classPathResource.getFile();
		    }
		    
		    FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
		    BufferedWriter bw = new BufferedWriter(fw);
		    bw.write(msg);
		    bw.newLine(); //버퍼에 개행 삽입
		    
		    bw.close();
		    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
	}
	
	
//	파일삭제
	public void fileDelete(String flieName) {
		try {
			ClassPathResource classPathResource = new ClassPathResource("file/"+flieName);
            boolean exists = classPathResource.exists();
//            System.out.println("exists = " + exists);
            
            File file = classPathResource.getFile();
            file.delete();
            
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

// 파일생성
	public void fileCreate(String fileName) {
		try {
			ClassPathResource classPathResource = new ClassPathResource("file");
            
            File file = new File(classPathResource.getURI().getPath()+"/"+fileName);
            FileWriter fw = new FileWriter(file, false) ;
            fw.flush();
            // 객체 닫기
            fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//로그파일 생성
	public void fileListCreate() {
		this.fileCreate("redis_sub01.log");
		this.fileCreate("redis_pub01.log");
		this.fileCreate("redis_pub02.log");
		this.fileCreate("redis_pub03.log");
		this.fileCreate("redis_pub04.log");
		this.fileCreate("redis_pub05.log");
		this.fileCreate("redis_pub06.log");
		this.fileCreate("redis_pub07.log");
		this.fileCreate("redis_pub08.log");
		this.fileCreate("redis_pub09.log");
		this.fileCreate("redis_pub10.log");
		this.fileCreate("redis_pub11.log");
		this.fileCreate("redis_pub12.log");
		this.fileCreate("redis_pub13.log");
		this.fileCreate("redis_pub14.log");
		this.fileCreate("redis_pub15.log");
		this.fileCreate("redis_pub16.log");
		this.fileCreate("redis_pub17.log");
		this.fileCreate("redis_pub18.log");
		this.fileCreate("redis_pub19.log");
		this.fileCreate("redis_pub20.log");
	}

	
//	파일라인수 확인
	public Integer fileLineCnt(String fileName) {
		try {
			ClassPathResource classPathResource = new ClassPathResource("file");
            
            InputStream is = new BufferedInputStream(new FileInputStream(classPathResource.getURI().getPath()+"/"+fileName));
            try {
                byte[] c = new byte[1024];
                int count = 0;
                int readChars = 0;
                boolean empty = true;
                while ((readChars = is.read(c)) != -1) {
                    empty = false;
                    for (int i = 0; i < readChars; ++i) {
                        if (c[i] == '\n') {
                            ++count;
                        }
                    }
                }
                return (count == 0 && !empty) ? 1 : count;
            } finally {
                is.close();
            }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
}
