package com.samjo.app.security.service;

import java.util.List;

import lombok.Data;

@Data
public class LoginVO {
	private String empId;
	private String pw;
	private String empName;
	private String custNo;
	private int ctNo;
	private String deptId;
	private int jobNo;
	private String permId;
	private String empStat;
	private List<String> roleMod;
}
