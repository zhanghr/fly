package com.helome.messagecenter.client.socket.message;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.SocketException;
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

public class TestConsultRejectMessage {
	private SocketClient client ;
    private Long senderId;
    private MessageMetaData messageMetaData;
    private MessageMetaData consultRejectmessageMetaData;
	@Before
	public void setUp() throws Exception {
		client = new SocketClient("127.0.0.1",CommonsUtils.getSocketPort());
		client.connect();
		senderId = UUIDUtil.longUUID();
	    messageMetaData = CommonsUtils.getReportMetaData(senderId);
	    consultRejectmessageMetaData = CommonsUtils.getConsultRejectMetaData(senderId,senderId,"test");
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		try {
			client.writeDate(messageMetaData);
			List<Map<String, Object>>  lists=client.getList();
			client.writeDate(consultRejectmessageMetaData);
			Thread.sleep(2000);
			client.readDateBlock(132);
			boolean result = false;
			for(Map<String, Object> map :lists){
                  if(map.containsKey("senderId")&&map.containsValue(senderId)){
                	 short code = (short) map.get("code");
                	 if(code==132){
                		 result = true; 
                	 }
                  }

			}
			Assert.assertTrue(result);
			
		} catch (SocketException e) {
		//	e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
