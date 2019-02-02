package com.ele.project.message.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ele.project.message.dao.MessageDao;
import com.ele.project.message.service.MessageService;

@Service("/messageService")
public class MessageServiceImpl implements MessageService{

	@Resource
	private MessageDao messageDao;
	
	public List<Map<String,Object>> getMessageList(Map params){
		return messageDao.getMessageList(params);
	}
	public int getMessageListCount(Map params){
		return messageDao.getMessageListCount(params);
	}
	public int insertMessage(Map params){
		return messageDao.insertMessage(params);
	}
	public int deleteMessageByid(Map params){
		return messageDao.deleteMessageByid(params);
	}
}
