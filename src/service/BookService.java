package service;

import java.util.List;
import java.util.Map;

import dao.BookDao;

public class BookService {
	private static BookService instance = null;

	private BookService() {

	}

	public static BookService getInstance() {

		if (instance == null) {
			instance = new BookService();
		}
		return instance;
	}
	
	BookDao bookDao = BookDao.getInstance();
	
	public List<Map<String, Object>> bookList() {
		
		return bookDao.bookList();
	}

	public Map<String, Object> bookDetail(List<Object> param) {
		
		return bookDao.bookDetail(param);
	}

	public void bookUpdate(int sel, List<Object> param) {
		bookDao.bookUpdate(sel, param);
		
	}

}
