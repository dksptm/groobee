package com.samjo.app.emp.service;

import java.util.Date;

import lombok.Data;

@Data
public class EmpVO {
	private String empId;
	private String ctNo;
	private String custNo;
	private String deptId;
	private String jobNo;
	private String empNo;
	private String pw;
	private String empName;
	private Date hireDt;
	private Date FireDt;
	private String empStat;
	private String signImg;
	private String empAddr1;
	private String empAddr2;
	private String empAddr3;
	private String empAddr4;
	private String empTel;
	private String emailAddr;
	
	// 직급명, 부서명
	private String jobTitle;
	private String deptName;
	 
}
