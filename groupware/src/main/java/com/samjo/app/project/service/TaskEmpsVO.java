package com.samjo.app.project.service;

import lombok.Data;

@Data
public class TaskEmpsVO {
	
	// 업무참여자
	private String taskNo;
	private String taskEmpId;
	private String deptId;
	private String deptName;
	private String cmplt;
	private String custNo;
}
