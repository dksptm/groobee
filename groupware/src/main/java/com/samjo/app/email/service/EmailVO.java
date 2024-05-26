package com.samjo.app.email.service;

import java.util.Date;

import lombok.Data;

@Data
public class EmailVO {
	
	// email TABLE
	private String senEmailNo;
	private String sender;
	private String title;
	private String cntn;
	private Date sentDt;
	private String emailStat;
	private String chainMailNo;
	private String custNo;
	
	// inbox TABLE
	private String recEmailNo;
	private String recp;
	private String refer;
	private String recpType;
	private Date readDt;
	
	// email_file TABLE
	private Integer fileNo;
	private String saveName;
	private String uplName;
	private String fileExt;
	private String fileSize;
	private Date fileDt;
	private String uplEmp;
	
	
}

