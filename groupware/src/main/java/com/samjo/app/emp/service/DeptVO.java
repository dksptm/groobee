package com.samjo.app.emp.service;

import java.util.List;

import lombok.Data;

@Data
public class DeptVO {
	private String deptId;
	private String deptName;
	
	private List<EmpVO> emps;
}
