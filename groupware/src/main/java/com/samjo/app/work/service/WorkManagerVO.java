package com.samjo.app.work.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class WorkManagerVO {

	private String deptId;
	private int empNo;
	private String empName;
	private String jobNo;
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date hireDt;
	private String empTel;
}
