package com.samjo.app.project.service;

import lombok.Data;

@Data
public class TaskEmpsVO {
	
	// 업무참여자
	private Integer taskNo;
	private String taskEmpId;
	private String deptId;
	private String deptName;
	private String cmplt;
	private String custNo;
	
	private Integer result;
	private String taskEmpName;
	private String codeName;
	// 참여자선택 체크 비활성화
	private boolean checked;
}
