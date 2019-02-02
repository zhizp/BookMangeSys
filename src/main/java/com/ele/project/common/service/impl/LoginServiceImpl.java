package com.ele.project.common.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ele.project.common.dao.LoginDao;
import com.ele.project.common.service.LoginService;
import com.ele.project.sysmanager.user.pojo.UserDTO;
import com.ele.project.util.MySendMailThread;

@Transactional
@Service("loginService")
public class LoginServiceImpl implements LoginService {
	@Resource
	private LoginDao loginDao;
	
	public UserDTO selectUser(UserDTO user) {
		return loginDao.selectUser(user);
	}
	public List<Map<String,Object>> getUserDTOByName(Map userMap){
		return loginDao.getUserDTOByName(userMap);
	}
	public int insertUser(Map userMap) {
		int i= loginDao.insertUser(userMap);
	    return i;
	}
	
	public List<Map<String,Object>> queryUserList(Map userMap){
		return loginDao.queryUserList(userMap);
	}
	public int queryUserCount(Map userMap) {
		return loginDao.queryUserCount(userMap);
	}
	public int deleteUserById(String id) {
		return loginDao.deleteUserById(id);
	}
	
	public int updateUserState(Map userMap) {
		return loginDao.updateUserState(userMap);
	}
	public Map<String,Object> getOldPwdByid(Map params){
		return loginDao.getOldPwdByid(params);
	}
	public int updatePasswordByid(Map params){
		return loginDao.updatePasswordByid(params);
	}
	public Map<String,Object> getUserInfoByUserid(Map params){
		return loginDao.getUserInfoByUserid(params);
	}
	public int updateUserByid(Map params) {
		return loginDao.updateUserByid(params);
	}
}
