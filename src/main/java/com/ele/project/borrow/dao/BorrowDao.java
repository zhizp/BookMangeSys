package com.ele.project.borrow.dao;

import java.util.List;
import java.util.Map;

public interface BorrowDao {

	public int borrowBook(Map params);
	public int returnBook(Map params);
	public List<Map<String,Object>> getBorrowedBookByUserOrBookId(Map params);
	public int getBorrowedBookCountByUserOrBookId(Map params);
	public List<Map<String,Object>> getCurrentBorrowedByUserid(Map params);
	public int getCurrentBorrowedCountByUserid(Map params);
	public List<Map<String,Object>> getHistoricalBorrowingByUserid(Map params);
	public int getHistoricalBorrowingCountByUserid(Map params);
	public int updateTotalborrowToUser(Map params);
	
}
