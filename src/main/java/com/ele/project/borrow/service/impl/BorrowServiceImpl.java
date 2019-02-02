package com.ele.project.borrow.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ele.project.borrow.dao.BorrowDao;
import com.ele.project.borrow.service.BorrowService;

@Service("/borrowService")
public class BorrowServiceImpl implements BorrowService {

	@Resource
	private BorrowDao borrowDao;
	
	public int borrowBook(Map params) {
		return borrowDao.borrowBook(params);
	}
	
	public int returnBook(Map params) {
		return borrowDao.returnBook(params);
	}
	
	public List<Map<String,Object>> getBorrowedBookByUserOrBookId(Map params){
		return borrowDao.getBorrowedBookByUserOrBookId(params);
	}
	public int getBorrowedBookCountByUserOrBookId(Map params){
		return borrowDao.getBorrowedBookCountByUserOrBookId(params);
	}
	public List<Map<String,Object>> getCurrentBorrowedByUserid(Map params){
		return borrowDao.getCurrentBorrowedByUserid(params);
	}
	public int getCurrentBorrowedCountByUserid(Map params) {
		return borrowDao.getCurrentBorrowedCountByUserid(params);
	}
	public List<Map<String,Object>> getHistoricalBorrowingByUserid(Map params){
		return borrowDao.getHistoricalBorrowingByUserid(params);
	}
	public int getHistoricalBorrowingCountByUserid(Map params) {
		return borrowDao.getHistoricalBorrowingCountByUserid(params);
	}
	public int updateTotalborrowToUser(Map params) {
		return borrowDao.updateTotalborrowToUser(params);
	}
	
}
