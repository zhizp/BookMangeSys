package com.ele.project.book.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ele.project.book.dao.BookDao;
import com.ele.project.book.service.BookService;

@Service("/bookService")
public class BookServiceImpl implements BookService{

	@Resource
	private BookDao bookDao;
	
	public List<Map<String,Object>> getBookList(Map bookMap){
		return bookDao.getBookList(bookMap);
	}
	
	public int getBookListCount(Map bookMap) {
		return bookDao.getBookListCount(bookMap);
	}
	
	public List<Map<String,Object>> searchBook(Map bookMap){
		return bookDao.searchBook(bookMap);
	}
	
	public int searchBookCount(Map bookMap) {
		return bookDao.searchBookCount(bookMap);
	}
	
	public Map<String,Object> getBookDetailByid(Map bookMap){
		return bookDao.getBookDetailByid(bookMap);
	}
	public int insertBook(Map params) {
		return bookDao.insertBook(params);
	}
	public int updateBook(Map params) {
		return bookDao.updateBook(params);
	}
	public int deleteBook(Map params) {
		return bookDao.deleteBook(params);
	}
}
