package com.ele.project.book.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
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

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.ele.project.book.service.BookService;
import com.ele.project.util.PageUtils;

import net.sf.json.JSONObject;

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
	 * @throws Exception 
	 */
	@RequestMapping("/addBook")
	@ResponseBody
	public Map<String,Object> addBook(HttpServletRequest req,HttpServletResponse response) throws Exception{
		Map<String, Object> params = PageUtils.getParameters(req);
		Map<String,Object> resultMap=new HashMap<String,Object>();
		//String coverimg=this.uploadPicture(req, response);
		params.put("id", UUID.randomUUID().toString());
		//params.put("coverimg", coverimg);
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
	
    
    
    @SuppressWarnings("deprecation")
	@RequestMapping("/uploadPicture")
    public void uploadPicture(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String destPath=""; 
    	String fileName="";
        //获取文件需要上传到的路径
        String path = request.getRealPath("/upload/wximg") + "/";
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdir();
        }

        request.setCharacterEncoding("utf-8");  //设置编码
        //获得磁盘文件条目工厂
        DiskFileItemFactory factory = new DiskFileItemFactory();

        //如果没以下两行设置的话,上传大的文件会占用很多内存，
        //设置暂时存放的存储室,这个存储室可以和最终存储文件的目录不同
        /**
         * 原理: 它是先存到暂时存储室，然后再真正写到对应目录的硬盘上，
         * 按理来说当上传一个文件时，其实是上传了两份，第一个是以 .tem 格式的
         * 然后再将其真正写到对应目录的硬盘上
         */
        factory.setRepository(dir);
        //设置缓存的大小，当上传文件的容量超过该缓存时，直接放到暂时存储室
        factory.setSizeThreshold(1024 * 1024);
        //高水平的API文件上传处理
        ServletFileUpload upload = new ServletFileUpload(factory);
        try {
            List<FileItem> list = upload.parseRequest(request);
            FileItem picture = null;
            for (FileItem item : list) {
                //获取表单的属性名字
                String name = item.getFieldName();
                if("user".equals(name)) {
                	name=UUID.randomUUID().toString();
                }
                //如果获取的表单信息是普通的 文本 信息
                if (item.isFormField()) {
                    //获取用户具体输入的字符串
                    String value = item.getString();
                    request.setAttribute(name, value);
                } else {
                    picture = item;
                }
            }

            //自定义上传图片的名字为user.jpg
            fileName = UUID.randomUUID().toString() + ".jpg";
            destPath = path + fileName;

            //真正写到磁盘上
            File file = new File(destPath);
            OutputStream out = new FileOutputStream(file);
            InputStream in = picture.getInputStream();
            int length = 0;
            byte[] buf = new byte[1024];
            // in.read(buf) 每次读到的数据存放在buf 数组中
            while ((length = in.read(buf)) != -1) {
                //在buf数组中取出数据写到（输出流）磁盘上
                out.write(buf, 0, length);
            }
            in.close();
            out.close();
        } catch (FileUploadException e1) {
        } catch (Exception e) {
        }


        PrintWriter printWriter = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        HashMap<String, Object> res = new HashMap<String, Object>();
        res.put("success", true);
        res.put("coverimg", "/upload/wximg/"+fileName);
        printWriter.write(JSON.toJSONString(res));
        printWriter.flush();
    }
    
    
    
    
    
	
	
	
}
