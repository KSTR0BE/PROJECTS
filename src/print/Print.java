package print;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class Print {
	public void printVar() {
		 System.out.println("==============================");
		 
	}
	
	public void printLn(int line) {
		for(int i = 0; i < line; i++) System.out.println();
	}
	
	
	public void printList(List<Map<String, Object>> freeList) {
		System.out.println("no\ttitle\tcontent\twriter\tregdate\tcount");
		for(Map<String, Object> map : freeList) {
			//오라클에서 JAVA로 올때
//			//Number 타입 -> BigDecimal <-long보다 더 큰 수 소숫점도 커버 가능 더블 롱 다 됨 
//			//Date 타입 -> TimeStamp 
//			//Varchar2 타입 -> String 으로 변환
//			//컨트롤 쉬프트 y 전체가 소문자로 바뀜
//			//date를 스트링으로 가져올려면 오라클에서 TO_CHAR로 바꿔서 가져온다
//			//그렇게 안하면 TimeStamp로
			BigDecimal 	no 		= (BigDecimal)map.get("NO");
			String 		title 	= (String)map.get("TITLE");
			String 		content	= (String)map.get("CONTENT");
			String 		writer	= (String)map.get("WRITER");
			String 		regdate = (String)map.get("REGDATE");
			BigDecimal 	count 	= (BigDecimal)map.get("COUNT");
			System.out.println(no + "\t" + title + "\t" + content + "\t" + writer + "\t" + regdate + "\t" + count);
		}
		System.out.println("1. 관리자");
		System.out.println("2. 회원");
		System.out.println("3. 홈");
	}
	
	 
	 
	public void printHome() {
		printVar();
		System.out.println("1. 관리자");
		System.out.println("2. 회원");
		System.out.println("3. 홈");
		printLn(4);
		printVar();
	 }
}
