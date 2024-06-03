package com.samjo.app.work.service;


import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class WorkPageDTO {
	private int page;
	private int startPage, endPage;
	private int totalCnt;
	private boolean prev, next;
	
	
	public WorkPageDTO (int page, int totalCnt) {
		this.page = page;
		this.totalCnt = totalCnt;
		int realEnd = (int) Math.ceil(totalCnt/5.0);
		
		this.endPage = (int) Math.ceil(page/5.0) * 5;
		this.startPage = this.endPage -4;
		
		this.endPage = this.endPage > realEnd ? realEnd : this.endPage;
		
		this.prev = this.startPage > 1;
		this.next = this.endPage < realEnd;
	}
}
