package com.ele.project.message.service;

import java.util.List;
import java.util.Map;

public interface MessageService {

	public List<Map<String,Object>> getMessageList(Map params);
	public int getMessageListCount(Map params);
	public int insertMessage(Map params);
	public int deleteMessageByid(Map params);
	
}
