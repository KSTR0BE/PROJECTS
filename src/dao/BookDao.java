package dao;

import java.util.List;
import java.util.Map;

import util.JDBCUtil;

public class BookDao {
	private static BookDao instance = null;

	private BookDao() {

	}

	public static BookDao getInstance() {

		if (instance == null) {
			instance = new BookDao();
		}
		return instance;
	}
	
	JDBCUtil jdbc = JDBCUtil.getInstance();
	
	public List<Map<String, Object>> bookList(){
		String sql = "SELECT B.*\r\n" + 
				"FROM JAVA_BOOK B, (SELECT BOOK_NO FROM JAVA_BOOKHOLD\r\n" + 
				"                            GROUP BY BOOK_NO) BH\r\n" + 
				"WHERE B.NO = BH.BOOK_NO\r\n" + 
				"ORDER BY 1";
		
		return jdbc.selectList(sql);
	}

	public Map<String, Object> bookDetail(List<Object> param) {
		String sql ="SELECT * \r\n" + 
					"FROM JAVA_BOOK B\r\n" + 
					"WHERE B.NO = ?";
		return jdbc.selectOne(sql, param);
	}

	public void bookUpdate(int sel, List<Object> param) {
		String sql = "UPDATE JAVA_BOOK "
					+ " SET ";
		// 이름 내용 저자
		if(sel == 1 || sel == 2) {
			sql+= "NAME = ? ";
			if(sel == 1) sql+=" , ";
		}
		if(sel == 1 || sel == 3) {
			sql+= "CONTENT = ? ";
			if(sel == 1) sql+=" , ";
		}
		if(sel == 1 || sel == 4) {
			sql+= "WRITER = ? ";
		}
		sql += "WHERE NO = ? ";
		
		
		jdbc.update(sql, param);
	}
}
