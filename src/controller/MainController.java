package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import print.Print;
import service.BookService;
import util.ScanUtil;
import util.View;

public class MainController extends Print {
	
	static public Map<String, Object> sessionStorage = new HashMap<>();
	BookService bookService = BookService.getInstance();
	public static void main(String[] args) {
		new MainController().start();
	}

	private void start() {
		View view = View.HOME;
		while (true) {
			switch (view) {
			case HOME:
				view = home();
				break;
			case BOOKLIST:
				view = bookList();
				break;
			case BOOKINSERT:
				view = bookInsert();
				break;
			case BOOKDETAIL:
				view = bookDetail();
				break;
			case BOOKUPDATE:
				view = bookUpdate();
				break;
			case BOOKDELETE:
				view = bookDelete();
				break;
			case MEMBER_BOOKLIST:
				view = memberBookList();
				break;
			case MEMBER_BOOKRENT:
				view = memberBookRent();
				break;
			case MEMBER_RENTLIST:
				view = memberRentList();
				break;
			case ADMIN:
				view = admin();
				break;
			case MEMBER:
				view = member();
				break;
			default:
				break;
			}
		}
	}
	private View memberBookList() {
		
		return View.MEMBER_BOOKLIST;
	}
	private View memberBookRent() {
		
		return View.MEMBER_BOOKRENT;
	}
	private View memberRentList() {
		
		return View.MEMBER_RENTLIST;
	}
	
	private View bookUpdate() {
		
		System.out.println("1. 전체");
		System.out.println("2. 이름");
		System.out.println("3. 내용");
		System.out.println("4. 저자");
		int sel = ScanUtil.nextInt("수정할 내용 선택 : ");
		
		List<Object> param = new ArrayList();
		if(sel== 1 || sel ==2) {
			String name = ScanUtil.nextLine("이름: ");
			param.add(name);
		}
		if(sel== 1 || sel ==3) {
			String content = ScanUtil.nextLine("내용: ");
			param.add(content);
		}
		if(sel== 1 || sel ==4) {
			String writer = ScanUtil.nextLine("저자: ");
			param.add(writer);
		}
		int bookNo = (int)sessionStorage.get("bookNo");
		param.add(bookNo);
		
		
		bookService.bookUpdate(sel, param);
		

		return View.BOOKLIST;
	}

	private View bookDelete() {
		
		return View.BOOKLIST;
	}

	private View bookDetail() {
		
		int bookNo = (int) sessionStorage.get("bookNo");
		List<Object> param = new ArrayList();
		param.add(bookNo);
		
		Map<String, Object> bookDetail = bookService.bookDetail(param);
		System.out.println(bookDetail);
		
		System.out.println("1. 도서 삭제");
		System.out.println("2. 도서 내용 변경");
		System.out.println("3. 관리자 홈");
		int sel = ScanUtil.menu();
		switch (sel) {
		case 1:  return View.BOOKDELETE;
		case 2:  return View.BOOKUPDATE;
		case 3:  return View.ADMIN;
		default: return View.BOOKDETAIL;
		}
	}

	private View bookInsert() {
		
		return View.BOOKLIST;
	}

	private View bookList() {
		
		List<Map<String, Object>> bookList = bookService.bookList();
		for(Map<String, Object> map : bookList) {
			System.out.println(map);
		}
		
		System.out.println("1. 도서 상세 조회");
		System.out.println("2. 관리자 홈");
		int sel = ScanUtil.menu();
		switch (sel) {
		case 1: int bookNo = ScanUtil.nextInt("책 번호 입력 : ");
        sessionStorage.put("bookNo", bookNo); 
				 return View.BOOKDETAIL;
		case 2:  return View.ADMIN;
		default: return View.BOOKLIST;
		}
	}
	private View member() {
		System.out.println("1. 도서 리스트 조회");
		System.out.println("2. 도서 대여");
		System.out.println("3. 도서 대여 내역 조회");
		int sel = ScanUtil.menu();
		switch (sel) {
		case 1:  return View.MEMBER_BOOKLIST;
		case 2:  return View.MEMBER_BOOKRENT;
		default: return View.MEMBER_RENTLIST;
		}
		
	}
	
	private View admin() { //원래 여기에 로그인 기능이 있어야함
		System.out.println("1. 도서리스트 조회");
		System.out.println("2. 도서 추가");
		int sel = ScanUtil.menu();
		switch (sel) {
		case 1:  return View.BOOKLIST;
		case 2:  return View.BOOKINSERT;
		default: return View.ADMIN;
		}
	}

	private View home() {
		
		printHome();
		int sel = ScanUtil.nextInt("메뉴를 선택하세요");
		switch (sel) {
		case 1:  return View.ADMIN;
		case 2:  return View.MEMBER;
		default: return View.HOME;
		}
	}

}