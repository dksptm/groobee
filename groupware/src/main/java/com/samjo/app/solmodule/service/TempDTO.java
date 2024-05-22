package com.samjo.app.solmodule.service;

import lombok.Data;

@Data
public class TempDTO {
	private int page; //현재페이지.
	private int startPage, endPage; // 현재 13이면 ->  << 11 12 13 14 15 ... 20 >>
	private boolean prev, next; // <<, >> 있는지 없는지.
	private int totalCnt;
	
	public TempDTO (int page, int totalCnt) {
		this.page = page;
		this.totalCnt = totalCnt;
		int realEnd = (int) Math.ceil(totalCnt/8.0);
		
		this.endPage = (int) Math.ceil(page/8.0) * 5;
		this.startPage = this.endPage - 4;
		
		this.endPage = this.endPage > realEnd ? realEnd : this.endPage;
		
		this.prev = this.startPage > 1;
		this.next = this.endPage < realEnd;
	}
}
