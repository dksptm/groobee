package com.samjo.app.email.service;

import lombok.Data;

@Data
public class EmailDTO {
	private int page; //현재 페이지
	private int startPage, endPage;
	private boolean prev, next;
	private int totalCnt;
	
	public EmailDTO (int page, int totalCnt) {
		this.page = page;
		this.totalCnt = totalCnt;
		int realEnd = (int) Math.ceil(totalCnt/15.0);
		
		this.endPage = (int) Math.ceil(page/15.0) * 5;
		this.startPage = this.endPage - 4;
		
		this.endPage = this.endPage > realEnd ? realEnd : this.endPage;
		
		this.prev = this.startPage > 1;
		this.next = this.endPage < realEnd;
	}
}