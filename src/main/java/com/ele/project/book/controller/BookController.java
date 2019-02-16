package com.ele.project.book.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ele.project.book.service.BookService;
import com.ele.project.util.PageUtils;

@Controller
@RequestMapping("/bookController")
public class BookController {

	@Resource
	private BookService bookService;
	
	/**
	 * 获取图书列表
	 * @param req  参数可为图书类型type：0:少儿;1:文学;2:历史;3:计算机;4:励志;5:美食;
	 * @return
	 */
	@RequestMapping("/getBookList")
	@ResponseBody
	public Map<String,Object> getBookList(HttpServletRequest req){
		Map<String, Object> params = PageUtils.getParameters(req);
		Map<String,Object> resultMap=new HashMap<String,Object>();
		List<Map<String,Object>> booklist=new ArrayList<Map<String,Object>>();
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
		booklist=bookService.getBookList(params);
		int count=bookService.getBookListCount(params);
		resultMap.put("total", count);
		resultMap.put("data", booklist);
		return resultMap;
	}
	
	/**
	 * 首页图书搜索功能
	 * @param req  参数可为书名、作者、出版社且形参必须为keyword
	 * @return
	 */
	@RequestMapping("/searchBook")
	@ResponseBody
	public Map<String,Object> searchBook(HttpServletRequest req){
		Map<String, Object> params = PageUtils.getParameters(req);
		Map<String,Object> resultMap=new HashMap<String,Object>();
		List<Map<String,Object>> booklist=new ArrayList<Map<String,Object>>();
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
		booklist=bookService.searchBook(params);
		int count=bookService.searchBookCount(params);
		resultMap.put("total", count);
		resultMap.put("data", booklist);
		return resultMap;
	}
	
	/**
	 * 根据图书id获取图书详情
	 * @param req 参数为图书id
	 * @return
	 */
	@RequestMapping("/getBookDetailByid")
	@ResponseBody
	public Map<String,Object> getBookDetailByid(HttpServletRequest req){
		Map<String, Object> params = PageUtils.getParameters(req);
		Map<String,Object> resultMap=new HashMap<String,Object>();
		
		Map<String,Object> bookDetail=bookService.getBookDetailByid(params);
		resultMap.put("data", bookDetail);
		return resultMap;
	}
	/**
	 * 添加图书信息
	 * @param req
	 * @param paramsMap
	 * @return
	 */
	@RequestMapping("/addBook")
	@ResponseBody
	public Map<String,Object> addBook(HttpServletRequest req){
		Map<String, Object> params = PageUtils.getParameters(req);
		Map<String,Object> resultMap=new HashMap<String,Object>();
		params.put("id", UUID.randomUUID().toString());
		int i=bookService.insertBook(params);
		if(i>0) {
			resultMap.put("rs", true);
			resultMap.put("msg", "添加图书成功");
			return resultMap;
		}
		resultMap.put("rs", false);
		resultMap.put("msg", "添加图书失败");
		return resultMap;
	}
	
	
	/**
	 * 修改图书信息
	 * @param req
	 * @param paramsMap
	 * @return
	 */
	@RequestMapping("/updateBook")
	@ResponseBody
	public Map<String,Object> updateBook(HttpServletRequest req){
		Map<String, Object> params = PageUtils.getParameters(req);
		Map<String,Object> resultMap=new HashMap<String,Object>();
		int i=bookService.updateBook(params);
		if(i>0) {
			resultMap.put("rs", true);
			resultMap.put("msg", "修改成功");
			return resultMap;
		}
		resultMap.put("rs", false);
		resultMap.put("msg", "修改失败");
		return resultMap;
	}
	
	
	/**
	 * 删除图书信息
	 * @param req
	 * @param paramsMap
	 * @return
	 */
	@RequestMapping("/deleteBook")
	@ResponseBody
	public Map<String,Object> deleteBook(HttpServletRequest req){
		Map<String, Object> params = PageUtils.getParameters(req);
		Map<String,Object> resultMap=new HashMap<String,Object>();
		int i=bookService.deleteBook(params);
		if(i>0) {
			resultMap.put("rs", true);
			resultMap.put("msg", "删除成功");
			return resultMap;
		}
		resultMap.put("rs", false);
		resultMap.put("msg", "删除失败");
		return resultMap;
	}
	/**
	 * 排行榜
	 * @param req
	 * @return
	 */
	@RequestMapping("/gethotbook")
	@ResponseBody
	public Map<String,Object> gethotbook(HttpServletRequest req){
		Map<String, Object> params = PageUtils.getParameters(req);
		Map<String,Object> resultMap=new HashMap<String,Object>();
		List<Map<String,Object>> booklist=new ArrayList<Map<String,Object>>();
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
		booklist=bookService.gethotbook(params);
		int count=bookService.gethotbookCount(params);
		resultMap.put("total", count);
		resultMap.put("data", booklist);
		return resultMap;
	}
	
	@RequestMapping("/getBookDetailByCallno")
	@ResponseBody
	public Map<String,Object> getBookDetailByCallno(HttpServletRequest req){
		Map<String, Object> params = PageUtils.getParameters(req);
		Map<String,Object> resultMap=new HashMap<String,Object>();
		
		List<Map<String,Object>> booklist=bookService.getBookDetailByCallno(params);
		if(!booklist.isEmpty()){
			resultMap.put("success", true);
			resultMap.put("info", booklist.get(0));
    	}else{
    		resultMap.put("success", false);
    		resultMap.put("msg", "没有此书籍!");
    	}
		return resultMap;
	}
	
	
	
	
	
}
