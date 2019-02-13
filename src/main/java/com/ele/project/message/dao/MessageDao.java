package com.ele.project.message.dao;

import java.util.List;
import java.util.Map;

public interface MessageDao {

	public List<Map<String,Object>> getMessageList(Map params);
	public int getMessageListCount(Map params);
	public int insertMessage(Map params);
	public int deleteMessageByid(String ids);
}
