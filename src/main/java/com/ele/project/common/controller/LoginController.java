package com.ele.project.common.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ele.project.common.SupportAction;
import com.ele.project.common.service.LoginService;
import com.ele.project.sysmanager.user.pojo.UserDTO;
import com.ele.project.util.PageUtils;

@Controller
@RequestMapping(value = "/")
public class LoginController extends SupportAction {
	@Resource 
	private LoginService loginService;	
	
	public LoginService getLoginService() {
		return loginService;
	}

	/**
     * 向用户登录页面跳转
     */
    @RequestMapping(value = "/toLogin")
    public String toLogin(){
        return  "login";
    }
    /**
     * 向用户注册页面跳转
     */
    @RequestMapping(value = "/toregist")
    public String toregist(){
    	return  "regist";
    }
    
    @RequestMapping(value = "/toindex")
    public String toindex(){
    	return  "user";
    }

    /**
     * 用户登录，根据用户登录名和密码判断数据库中是否有此用户
     * @param user
     * @param model
     * @param session
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "/login")
    @ResponseBody
    public Map<String, Object> login(HttpServletRequest req) throws IOException{
    	Map<String, Object> paramsMap = PageUtils.getParameters(req);
    	Map<String, Object> result = new HashMap<String, Object>();
    	try {
		 UserDTO user=new UserDTO();
	     //获取登录名和密码
		 if(paramsMap.containsKey("username")){
			 user.setUsername(paramsMap.get("username").toString());
         }
		 if(paramsMap.containsKey("password")){
			 user.setPassword(paramsMap.get("password").toString());
		 }
	     List<Map<String,Object>> userlist = loginService.getUserDTOByName(paramsMap);
	     if(userlist.size()>0) {
	    	 UserDTO returnUser=loginService.selectUser(user);
	    	 if(returnUser!=null) {
		    	 result.put("success", true);
				 result.put("userid", returnUser.getId());
				 req.getSession().setAttribute("user", returnUser);
				 req.setAttribute("user", returnUser);
	    	 }else {
	    		 result.put("success", false);
		    	 result.put("msg", "密码错误，请重新输入!");
	    	 }
	     }else {
	    	 result.put("success", false);
	    	 result.put("msg", "没有此用户!");
	     }
    	}catch(Exception e) {
    		e.printStackTrace();
			result.put("success", false);
			result.put("msg", "登录失败," + e.getMessage());
    	}
    	return result;
    }

    /**
     * 注册或者管理员添加用户
     * @param req
     * @param paramsMap
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/registe")
    @ResponseBody
    public Map<String, Object> registe(HttpServletRequest req,@RequestBody Map<String, Object> paramsMap) throws IOException{
    	Map<String, Object> result = new HashMap<String, Object>();
    	try {
		Map<String,Object> userMap=new HashMap<String,Object>();
	     //获取登录名和密码
		 if(paramsMap.containsKey("username")){
			 userMap.put("username", paramsMap.get("username").toString());
         }
		 if(paramsMap.containsKey("password")){
			 userMap.put("password",paramsMap.get("password").toString());
		 }
		 List<Map<String,Object>> hasList=loginService.getUserDTOByName(userMap);
		 if(hasList.size()>0) {
			 result.put("success", false);
			 result.put("msg", "用户名已存在，请重新输入!");
			 return result;
		 }
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	 String nowdate=sdf.format(new Date());
		 userMap.put("registerdate", nowdate);
		 userMap.put("id", UUID.randomUUID().toString());
	     int i = loginService.insertUser(userMap);
	     if(i>0) {
	    	 result.put("success", true);
	     }else {
	    	 result.put("success", false);
	    	 result.put("msg", "注册失败");
	     }
    	}catch(Exception e) {
    		e.printStackTrace();
			result.put("success", false);
			result.put("msg", "注册失败," + e.getMessage());
    	}
    	return result;
    }
    
    /**
     * 向主页面跳转
     */
    @RequestMapping(value = "/main")
    public String toIndex(HttpServletResponse response,HttpServletRequest request){
    	UserDTO user=(UserDTO)request.getSession().getAttribute("user");
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	String nowdate=sdf.format(new Date());
    	request.setAttribute("nowdate", nowdate);
    	request.getSession().setAttribute("user", user);
        return  "main";
    }
    
    
    @RequestMapping(value = "/logout")
    public String logout(HttpSession session){
        //清除session
        session.invalidate();
        //重定向到登录页面的跳转方法
        return "redirect:toLogin";
    }
    
    /**
     * 管理员-获取用户列表
     * @param req 参数可为账号username，姓名name，身份证号cardid
     * @return
     */
    @RequestMapping("/userlist")
	@ResponseBody
	public Map<String, Object> userlist(HttpServletRequest req) {
		List<Map<String,Object>> userlist=new ArrayList<Map<String,Object>>();
    	Map<String, Object> result = new HashMap<String, Object>();
    	Map<String, Object> params = PageUtils.getParameters(req);
    	UserDTO user=(UserDTO) req.getSession().getAttribute("user");
    	String username=user.getUsername();
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
		params.put("username", username);
    	userlist=loginService.queryUserList(params);
    	int count=loginService.queryUserCount(params);
		result.put("total", count);
		result.put("data", userlist);
    	return result;
	}
    
    /**
     * 根据用户id删除用户
     * @param req userid
     * @return
     */
    @RequestMapping("/deleteUserById")
    @ResponseBody
    public Map<String, Object> deleteUserById(HttpServletRequest req) {
    	Map<String, Object> params = PageUtils.getParameters(req);
    	Map<String, Object> returnMap = new HashMap<String, Object>();
    	int i=loginService.deleteUserById(params.get("userid").toString());
    	if(i>0) {
    		returnMap.put("rs", true);
    		return returnMap;
    	}
    	returnMap.put("rs", false);
    	return returnMap;
    }
    
    
    
    /**
     * 验证原密码是否输入正确
     * @param req
     * @return
     */
    @RequestMapping("/checkOldPwdByid")
    @ResponseBody
    public Map<String, Object> checkOldPwdByid(HttpServletRequest req) {
    	Map<String, Object> params = PageUtils.getParameters(req);
    	Map<String, Object> returnMap = new HashMap<String, Object>();
    	Map<String, Object> userMap=loginService.getOldPwdByid(params);
    	if(!userMap.isEmpty()) {
    		returnMap.put("rs", true);
    		return returnMap;
    	}
    	returnMap.put("rs", false);
		returnMap.put("msg", "原密码不正确");
    	return returnMap;
    }
    
    
    /**
     * 修改用户密码
     * @param req
     * @return
     */
    @RequestMapping("/updatePasswordByid")
    @ResponseBody
    public Map<String, Object> updatePasswordByid(HttpServletRequest req) {
    	Map<String, Object> params = PageUtils.getParameters(req);
    	Map<String, Object> returnMap = new HashMap<String, Object>();
    	Map<String, Object> oldMap=loginService.checkoldPasswordByid(params);
    	if(oldMap==null) {
    		returnMap.put("rs", false);
    		returnMap.put("msg", "原密码输入错误");
    		return returnMap;
    	}
    	int i=loginService.updatePasswordByid(params);
    	if(i>0) {
    		returnMap.put("rs", true);
    		returnMap.put("msg", "修改成功");
    		return returnMap;
    	}
    	returnMap.put("rs", false);
    	returnMap.put("msg", "修改失败");
    	return returnMap;
    }
    
    
    /**
     * 我的资料
     * @param req
     * @return
     */
    @RequestMapping("/getUserInfoByUserid")
    @ResponseBody
    public Map<String, Object> getUserInfoByUserid(HttpServletRequest req) {
    	Map<String, Object> params = PageUtils.getParameters(req);
    	Map<String, Object> returnMap = new HashMap<String, Object>();
    	returnMap=loginService.getUserInfoByUserid(params);
    	
    	return returnMap;
    }
    
    /**
     * 我的资料
     * @param req
     * @return
     */
    @RequestMapping("/updateUserByid")
    @ResponseBody
    public Map<String, Object> updateUserByid(HttpServletRequest req) {
    	Map<String, Object> params = PageUtils.getParameters(req);
    	Map<String, Object> returnMap = new HashMap<String, Object>();
    	int i =loginService.updateUserByid(params);
    	if(i<0) {
    		returnMap.put("rs", false);
    		returnMap.put("msg", "修改用户信息失败");
    	}
		returnMap.put("rs", true);
		returnMap.put("msg", "修改用户信息成功");
    	return returnMap;
    }
    
    
    
    
	
}
