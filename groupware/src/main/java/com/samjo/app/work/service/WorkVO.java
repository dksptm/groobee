package com.samjo.app.work.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class WorkVO {
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date sysdate;
	private String empId;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dt;
	@DateTimeFormat(pattern = "HH-MI-SS")
	private Date wkIn;
	@DateTimeFormat(pattern = "HH-MI-SS")
	private Date wkOut;
	private String wkInLoc;
	private String wkOutLoc;
	private String wkYn;
	private String wkStat;
	private String wkSite;
	private int empNo;
	private String empName;

}
