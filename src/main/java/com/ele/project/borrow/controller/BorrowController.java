package com.ele.project.borrow.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ele.project.borrow.service.BorrowService;
import com.ele.project.util.PageUtils;

@Controller
@RequestMapping("/borrowController")
public class BorrowController {
	@Resource
	private BorrowService borrowService;
	
	/**
	 * 借书 
	 * @param req传入userid，bookid
	 * @return
	 */
	@RequestMapping("/borrowBook")
	@ResponseBody
	public Map<String,Object> borrowBook(HttpServletRequest req){
		Map<String, Object> params = PageUtils.getParameters(req);
		Map<String,Object> resultMap=new HashMap<String,Object>();
		
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	//String nowdate=sdf.format(new Date());
    	
    	Calendar cal = Calendar.getInstance();
    	Date borrowdate = cal.getTime();
    	cal.add(Calendar.DATE, 30);
    	Date returningdate = cal.getTime();
    	
		params.put("id", UUID.randomUUID().toString());
		params.put("borrowdate", borrowdate);
		params.put("returningdate", returningdate);
		int i=borrowService.borrowBook(params);
		if(i<0) {
			resultMap.put("rs", false);
			resultMap.put("msg", "借阅失败");
			return resultMap;
		}
		borrowService.updateTotalborrowToUser(params);//用户表累计借书+1
		borrowService.updateCollectnumToBook(params);//图书可借数-1
		resultMap.put("rs", true);
		resultMap.put("msg", "借阅成功");
		return resultMap;
	}
	/**
	 * 根据用户账号名或图书条形码查询未还图书详情 
	 * @param req 传入用户id，图书id
	 * @return
	 */
	@RequestMapping("/getCurrentBorrowBookByUserOrBookId")
	@ResponseBody
	public Map<String,Object> getCurrentBorrowBookByUserOrBookId(HttpServletRequest req){
		Map<String, Object> params = PageUtils.getParameters(req);
		Map<String,Object> resultMap=new HashMap<String,Object>();
		
		List<Map<String,Object>> books=borrowService.getCurrentBorrowBookByUserOrBookId(params);
		if(!books.isEmpty()){
			resultMap.put("success", true);
			resultMap.put("books", books);
    	}else{
    		resultMap.put("success", false);
    		resultMap.put("msg", "没有记录!");
    	}
//		resultMap.put("data", books);
		return resultMap;
	}
	
	/**
	 * 还书 
	 * @param req 传入用户id，图书id
	 * @return
	 */
	@RequestMapping("/returnBooks")
	@ResponseBody
	public Map<String,Object> returnBooks(HttpServletRequest req){
		Map<String, Object> params = PageUtils.getParameters(req);
		Map<String,Object> resultMap=new HashMap<String,Object>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowdate=sdf.format(new Date());
		
		params.put("returneddate", nowdate);
		int i=borrowService.returnBooks(params);
		if(i<0) {
			resultMap.put("rs", false);
			resultMap.put("msg", "归还失败");
			return resultMap;
		}
		resultMap.put("rs", true);
		resultMap.put("msg", "归还成功");
		return resultMap;
	}
	
	/**
	 * 还书 
	 * @param req 传入用户id，图书id
	 * @return
	 */
	@RequestMapping("/returnBook")
	@ResponseBody
	public Map<String,Object> returnBook(HttpServletRequest req){
		Map<String, Object> params = PageUtils.getParameters(req);
		Map<String,Object> resultMap=new HashMap<String,Object>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowdate=sdf.format(new Date());
		
		params.put("returneddate", nowdate);
		int i=borrowService.returnBook(params);
		if(i<0) {
			resultMap.put("rs", false);
			resultMap.put("msg", "归还失败");
			return resultMap;
		}
		resultMap.put("rs", true);
		resultMap.put("msg", "归还成功");
		return resultMap;
	}
	
	
	/**
	 * 根据用户账号名或图书条形码查询图书详情 
	 * @param req 传入用户id，图书id
	 * @return
	 */
	@RequestMapping("/getBorrowedBookByUserOrBookId")
	@ResponseBody
	public Map<String,Object> getBorrowedBookByUserOrBookId(HttpServletRequest req){
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
		List<Map<String,Object>> books=borrowService.getBorrowedBookByUserOrBookId(params);
		int count=borrowService.getBorrowedBookCountByUserOrBookId(params);
		resultMap.put("data", books);
		resultMap.put("total", count);
		return resultMap;
	}
	
	/**
	 * 当前借阅图书列表
	 * @param req 传入用户id
	 * @return
	 */
	@RequestMapping("/getCurrentBorrowedByUserid")
	@ResponseBody
	public Map<String,Object> getCurrentBorrowedByUserid(HttpServletRequest req){
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
		List<Map<String,Object>> CurrentBorrowedBooks=borrowService.getCurrentBorrowedByUserid(params);
		int count=borrowService.getCurrentBorrowedCountByUserid(params);
		
		resultMap.put("data", CurrentBorrowedBooks);
		resultMap.put("count", count);
		
		return resultMap;
	}
	
	/**
	 * 借阅历史，借阅状态为已还
	 * @param req 传入用户id
	 * @return
	 */
	@RequestMapping("/getHistoricalBorrowingByUserid")
	@ResponseBody
	public Map<String,Object> getHistoricalBorrowingByUserid(HttpServletRequest req){
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
		List<Map<String,Object>> historicalBooks=borrowService.getHistoricalBorrowingByUserid(params);
		int count=borrowService.getHistoricalBorrowingCountByUserid(params);
		
		resultMap.put("data", historicalBooks);
		resultMap.put("count", count);
		
		return resultMap;
	}
	
	/**
	 * 根据借阅编码获取借阅信息
	 * @param req id
	 * @return
	 */
	@RequestMapping("/getCurrentBorrowBookByBorrowId")
	@ResponseBody
	public Map<String,Object> getCurrentBorrowBookByBorrowId(HttpServletRequest req){
		Map<String, Object> params = PageUtils.getParameters(req);
		Map<String,Object> resultMap=new HashMap<String,Object>();
		
		List<Map<String,Object>> borrows=borrowService.getCurrentBorrowBookByBorrowId(params);
		if(!borrows.isEmpty()){
			resultMap.put("success", true);
			resultMap.put("info", borrows.get(0));
    	}else{
    		resultMap.put("success", false);
    		resultMap.put("msg", "没有记录!");
    	}
//		resultMap.put("data", books);
		return resultMap;
	}
	
	
	

}
