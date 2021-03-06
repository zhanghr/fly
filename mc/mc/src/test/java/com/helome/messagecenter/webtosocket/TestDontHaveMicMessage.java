package com.helome.messagecenter.webtosocket;

import static org.junit.Assert.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.helome.messagecenter.client.socket.MessageMetaData;
import com.helome.messagecenter.client.socket.SocketClient;
import com.helome.messagecenter.client.utils.CommonsUtils;
import com.helome.messagecenter.client.utils.UUIDUtil;
import com.helome.messagecenter.client.websocket.BlockWebSocketClient;
import com.helome.messagecenter.utils.JsonUtils;
import com.helome.messagecenter.utils.SocketStateThread;
import com.helome.messagecenter.utils.WebThread;

public class TestDontHaveMicMessage {
	private URI uri;
	private List<String> values ;
	private Long senderId;
	private Long receiverId;
	
	private SocketClient scoketClient;
    private MessageMetaData messageMetaData;
	@Before
	public void setUp() throws Exception {
		uri = URI.create(CommonsUtils.getAddress());
		 values = new ArrayList<String>();
		 Map<String,Object> map = new HashMap<String,Object>();
		 senderId = UUIDUtil.longUUID();
		 receiverId = UUIDUtil.longUUID();
		 map.put("code", 1);
		 map.put("id", senderId);
		 map.put("token", "junit:test");
		 map.put("type", 1);
		 String jsonStr = JsonUtils.stringify(map);
	     values.add(jsonStr);

	     Map<String,Object> commentmap = new HashMap<String,Object>();
	     commentmap.put("code", 252);
	     commentmap.put("senderId", senderId);
	     commentmap.put("receiverId", receiverId);
	     commentmap.put("senderName", "DontHaveMicMessage:"+senderId);
	     commentmap.put("receiverName", "DontHaveMicMessage"+senderId);
	     commentmap.put("date", new Date());
	     commentmap.put("sessionId", senderId);
	     String commentjsonStr = JsonUtils.stringify(commentmap);
	     values.add(commentjsonStr);
	     
	     this.scoketClient = new SocketClient("127.0.0.1", CommonsUtils.getSocketPort());
	     this.scoketClient.connect();
	     messageMetaData = CommonsUtils.getReportMetaData(receiverId);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		try {
			SocketStateThread socketSender = new SocketStateThread(this.scoketClient, this.messageMetaData, 3);
			BlockWebSocketClient webSocketSender = new BlockWebSocketClient(uri,values);
			WebThread webClient = new WebThread(webSocketSender);
			socketSender.start();
			Thread.sleep(2000);
			webClient.start();
			Thread.sleep(5000);
			List<Map<String, Object>>  lists = scoketClient.getList();
			boolean result = false;
			for(Map<String, Object> map :lists){
				System.out.println(map);
				 short code = (short) map.get("code");
				 if(code==252){
					Long sedId =  (Long) map.get("receiverId");
					if(sedId.longValue()==receiverId ){
						Integer realyLength = (Integer) map.get("realyLength");
						Integer length = (Integer) map.get("length");
						if(realyLength.intValue()==length.intValue()){
							result = true;
						}
					}
				 }
			}
			 Assert.assertTrue(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}

}
