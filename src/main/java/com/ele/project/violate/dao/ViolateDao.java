package com.ele.project.violate.dao;

import java.util.List;
import java.util.Map;

public interface ViolateDao {

	public int insertViolate(Map params);
	public List<Map<String,Object>> getViolateinfo(Map params);
	public int getViolateinfoCount(Map params);
	public int deleteViolate(List<String> params);
}
