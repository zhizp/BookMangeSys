package com.ele.project.violate.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ele.project.violate.dao.ViolateDao;
import com.ele.project.violate.service.ViolateService;

@Service("/violateService")
public class ViolateServiceImpl implements ViolateService {

	@Resource
	private ViolateDao violateDao;
	
	public int insertViolate(Map params) {
		return violateDao.insertViolate(params);
	}
	
	public List<Map<String,Object>> getViolateinfo(Map params){
		return violateDao.getViolateinfo(params);
	}
	public List<Map<String,Object>> getViolatemnglist(Map params){
		return violateDao.getViolatemnglist(params);
	}
	public int getViolateinfoCount(Map params) {
		return violateDao.getViolateinfoCount(params);
	}
	public int deleteViolate(List<String> params) {
		return violateDao.deleteViolate(params);
	}
}
