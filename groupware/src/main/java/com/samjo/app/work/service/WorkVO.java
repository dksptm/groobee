package com.samjo.app.work.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class WorkVO {
	private int empNo;
	private String empName;
	private String empId;
	private String deptId;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date sysdate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dt;
	@DateTimeFormat(pattern = "HH:mm")
	private Date wkIn;
	@DateTimeFormat(pattern = "HH:mm")
	private Date wkOut;
	private String wkInLoc;
	private String wkOutLoc;
	private String wkYn;
	private String wkStat;
	private String wkSite;
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date hireDt;
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date fireDt;
	private String empTel;
	private String jobNo;

}
