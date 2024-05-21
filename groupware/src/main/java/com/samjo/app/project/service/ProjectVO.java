package com.samjo.app.project.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ProjectVO {
	
	// 업무공통
	private int taskNo;
	private String taskName;
	private String taskPurpose;
	private String taskCntn;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date taskStartDt;
	private Date taskDueDt;
	private String taskType;
	private String standardNo;
	private String prjtMat;
	private String deptId;
	
	//상시업무기준
	private String reguId;
	private String creType;
	private String crePerd;
	private String active;
	private Date fileDt;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String respEmpId;
	
	//프로젝트
	private String prjtId;
	private String prjtName;
	private String prjtStat;
	private String smry;
	private String purpose;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date prjtStartDt;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date prjtDueDT;
	private String respMngrId;
	
	
	//협력업체
	private int coopCoNo;
	private String coName;
	private String coTel;
	private String pic;
	private String custAddr;
	private String cntn; 
	
	// 업무참여자
	private String taskEmpId;
	private String deptName;
	private String cmplt;
	
	private String empName;
	// 업무문서
	private String docNo;
	
	private String abc;



	
}
