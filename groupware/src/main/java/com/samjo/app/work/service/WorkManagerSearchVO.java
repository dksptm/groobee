package com.samjo.app.work.service;



import lombok.Data;
@Data
public class WorkManagerSearchVO {

	private int page;
	private int startPage, endPage;
	private String filter;
	private String empId;
	private boolean prev, next;
	private int totalCnt;
	private String permId;
	private String custNo;
	private String deptId;
}
