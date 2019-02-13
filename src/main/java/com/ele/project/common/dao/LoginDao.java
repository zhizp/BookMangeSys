package com.ele.project.common.dao;

import java.util.List;
import java.util.Map;

import com.ele.project.sysmanager.user.pojo.UserDTO;

public interface LoginDao {

	public UserDTO selectUser(UserDTO user);
	public List<Map<String,Object>> getUserDTOByName(Map userMap);
	public int insertUser(Map userMap);
	public List<Map<String,Object>> queryUserList(Map userMap);
	public int queryUserCount(Map userMap);
	public int deleteUserById(String id);
	public int updateUserState(Map userMap);
	public Map<String,Object> getOldPwdByid(Map params);
	public int updatePasswordByid(Map params);
	public Map<String,Object> checkoldPasswordByid(Map params);
	public Map<String,Object> getUserInfoByUserid(Map params);
	public int updateUserByid(Map params);
}
