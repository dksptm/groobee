package com.samjo.app.work.service;

import org.springframework.format.annotation.DateTimeFormat;

public class WorkManagerVO {

	private String deptId;
	private int empNo;
	private String empName;
	private String jobNo;
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private String hireDt;
	private String empTel;
}
