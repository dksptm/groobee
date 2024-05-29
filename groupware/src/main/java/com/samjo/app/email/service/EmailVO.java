package com.samjo.app.email.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;


import lombok.Data;

@Data
public class EmailVO {
	
	// email TABLE
	private String senEmailNo;
	private String sender;
	private String title;
	private String cntn;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date sentDt;
	private String emailStat;
	private String chainMailNo;
	private String custNo;
	
	// inbox TABLE
	private String recEmailNo;
	private String recp;
	private String refer;
	private String recpType;
	private Date readDt; //안 쓰는 값, 추후 삭제
	
	// email_file TABLE
	private Integer fileNo;
	private String saveName;
	private String uplName;
	private String fileExt;
	private String fileSize;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fileDt;
	private String uplEmp;
	
	
}

