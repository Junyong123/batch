package kr.or.ddit.yogult.model;

import lombok.Data;

// getter setter 라이브러리 --> LOMBOK
@Data
public class DailyVO {
	private int cid; // 고객번호
	private int pid; // 제품번호
	private String dt;  // 실적일자 (일요일:1)
	private int cnt; // 제품수량
	
	public DailyVO(){
		
	}
	
}