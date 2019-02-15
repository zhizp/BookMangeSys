package com.ele.project.violate.controller;

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

import com.ele.project.util.PageUtils;
import com.ele.project.violate.service.ViolateService;

@Controller
@RequestMapping("/violateController")
public class ViolateController {

	@Resource
	private ViolateService violateService;
	
	/**
	 * 添加超期信息
	 * @param req
	 * @param paramsMap
	 * @return
	 */
	@RequestMapping("/addViolate")
	@ResponseBody
	public Map<String,Object> addViolate(HttpServletRequest req){
		Map<String, Object> params = PageUtils.getParameters(req);
		Map<String,Object> resultMap=new HashMap<String,Object>();
		params.put("id", UUID.randomUUID().toString());
		int i=violateService.insertViolate(params);
		if(i>0) {
			resultMap.put("rs", true);
			resultMap.put("msg", "添加超期信息成功 ");
		}
		resultMap.put("rs", false);
		resultMap.put("msg", "添加超期信息失败");
		return resultMap;
	}
	
	
	/**
	 * 违章缴款详情
	 * @param req 传入username
	 * @return
	 */
	@RequestMapping("/getViolateinfo")
	@ResponseBody
	public Map<String,Object> getViolateinfo(HttpServletRequest req){
		Map<String, Object> params = PageUtils.getParameters(req);
		Map<String,Object> resultMap=new HashMap<String,Object>();
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
		List<Map<String,Object>> vioList=violateService.getViolateinfo(params);
		int count=violateService.getViolateinfoCount(params);
		
		resultMap.put("data", vioList);
		resultMap.put("count", count);
		return resultMap;
	}
	/**
	 * 违章管理查询
	 * @param req 传入账号、书名或条形码
	 * @return
	 */
	@RequestMapping("/getViolatemnglist")
	@ResponseBody
	public Map<String,Object> getViolatemnglist(HttpServletRequest req){
		Map<String, Object> params = PageUtils.getParameters(req);
		Map<String,Object> resultMap=new HashMap<String,Object>();
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
		List<Map<String,Object>> vioList=violateService.getViolatemnglist(params);
		
		resultMap.put("data", vioList);
		return resultMap;
	}
	
	/**
	 * 缴纳罚款
	 * @param req 传入需缴纳罚款的id,可为多条
	 * @return
	 */
	@RequestMapping("/payViolate")
	@ResponseBody
	public Map<String,Object> payViolate(HttpServletRequest req){
		Map<String, Object> params = PageUtils.getParameters(req);
		Map<String,Object> resultMap=new HashMap<String,Object>();
		String ids="";
		if(params.get("ids").toString().isEmpty()){
			ids= "('')";
		}else {
			StringBuffer temp = new StringBuffer();
			temp.append("(");
			String[] strArray=params.get("ids").toString().split(",");
			if (strArray != null && strArray.length > 0 ) {
				for (int i = 0; i < strArray.length; i++) {
					temp.append("'");
					temp.append(strArray[i]);
					temp.append("'");
					if (i !=  (strArray.length-1) ) {
						temp.append(",");
					}
				}
			}
			temp.append(")");
			ids=temp.toString();
		}
		int i=violateService.deleteViolate(ids);
		if(i<=0) {
			resultMap.put("rs", false);
			resultMap.put("msg", "缴纳失败");
			return resultMap;
		}
		resultMap.put("rs", true);
		resultMap.put("msg", "缴纳成功");
		return resultMap;
	}
	
	//
	
}
