package com.ele.project.message.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ele.project.message.service.MessageService;
import com.ele.project.util.PageUtils;

@Controller
@RequestMapping("/messageController")
public class MessageController {

	@Resource
	private MessageService messageService;
	
	/**
	 * 消息中心-消息列表
	 * @param req 传入userid 
	 * @return
	 */
	@RequestMapping("/getMessageList")
	@ResponseBody
	public Map<String,Object> getMessageList(HttpServletRequest req){
		Map<String, Object> params = PageUtils.getParameters(req);
		Map<String,Object> resultMap=new HashMap<String,Object>();
		List<Map<String,Object>> messageList=new ArrayList<Map<String,Object>>();
		int page=0;
		int pagesize=10;
		if(params.get("page")!=null) {
			page=Integer.parseInt(params.get("page").toString());
		}
		if(params.get("pagesize")!=null) {
			pagesize=Integer.parseInt(params.get("pagesize").toString());
		}
		params.put("page", page);
		params.put("pagesize", pagesize);
		messageList=messageService.getMessageList(params);
		int count=messageService.getMessageListCount(params);
		resultMap.put("total", count);
		resultMap.put("data", messageList);
		return resultMap;
	}
	
	/**
	 * 新增提醒消息
	 * @param req 传入userid
	 * @return
	 */
	@RequestMapping("/addList")
	@ResponseBody
	public Map<String,Object> addList(HttpServletRequest req){
		Map<String, Object> params = PageUtils.getParameters(req);
		Map<String,Object> resultMap=new HashMap<String,Object>();
		params.put("id", UUID.randomUUID().toString());
		int i=messageService.insertMessage(params);
		if(i<0) {
			resultMap.put("rs", false);
			resultMap.put("msg", "新增消息失败");
		}
		resultMap.put("rs", true);
		resultMap.put("msg", "新增消息成功");
		return resultMap;
	}
	/**
	 * 删除消息
	 * @param req 传入id
	 * @return
	 */
	@RequestMapping("/deleteMessageByid")
	@ResponseBody
	public Map<String,Object> deleteMessageByid(HttpServletRequest req){
		Map<String, Object> params = PageUtils.getParameters(req);
		Map<String,Object> resultMap=new HashMap<String,Object>();
		int i=messageService.deleteMessageByid(params);
		if(i<0) {
			resultMap.put("rs", false);
			resultMap.put("msg", "删除消息失败");
		}
		resultMap.put("rs", true);
		resultMap.put("msg", "删除消息成功");
		return resultMap;
	}
	
}
