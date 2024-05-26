package com.samjo.app.work.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class WorkSearchVO {
	private int page;
	private String empName;
	private String empId;
	private int empNo;
	private String wkYn;
	private String wkStat;
	private String wkSite;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date oneDate;
	private int totalCnt;
	/*
	 * @DateTimeFormat(pattern = "yyyy/MM/dd") private Date twoDate;
	 */
	
	
}
