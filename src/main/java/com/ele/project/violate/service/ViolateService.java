package com.ele.project.violate.service;

import java.util.List;
import java.util.Map;

public interface ViolateService {

	public int insertViolate(Map params);
	public List<Map<String,Object>> getViolateinfo(Map params);
	public List<Map<String,Object>> getViolatemnglist(Map params);
	public int getViolateinfoCount(Map params);
	public int deleteViolate(List<String> params);
}
