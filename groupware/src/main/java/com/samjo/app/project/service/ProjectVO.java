package com.samjo.app.project.service;

import java.util.Date;
import java.util.List;

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
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date taskDueDt;
	private String taskType;
	private String standardNo;
	private String prjtMat;
	private String deptId;
	private String deptName;
	
	//상시업무기준
	private String reguId;
	private String creType;
	private String crePerd;
	private String active;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fileDt;
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
	private Date prjtDueDt;
	private String respMngrId;
	
	//협력업체
	private int coopCoNo;
	private String coName;
	private String coTel;
	private String pic;
	private String custAddr;
	private String cntn; 
	
	// 업무참여
    List<TaskEmpsVO> taskEmps;
    private String taskStat;
    private String taskEmpId;
    
	// 업무문서
	private String docNo;
	
	private String abc;
	
	private String custNo;


	
}
