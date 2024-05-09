package com.samjo.app.work.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class WorkVO {
	private String empId;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dt;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date wkIn;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date wkOut;
	private String wkInLoc;
	private String wkOutLoc;
	private String wkYn;
	private String wkStat;
	private String wkSite;

}
