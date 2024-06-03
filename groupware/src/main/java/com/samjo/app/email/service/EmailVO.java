package com.samjo.app.email.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class EmailVO {
	
	// email TABLE
	private String senEmailNo;
	private String sender;
	private String senderName;
	private String title;
	private String cntn;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date sentDt;
	private String emailStat;
	private String chainMailNo;
	private String custNo;
	private String sstat;
	
	// inbox TABLE
	private String recEmailNo;
	private String recp;
	private String recpName;
	private String refer;
	private String referName;
	private String recpType;
	private Date readDt; //안 쓰는 값, 추후 삭제
	private String rstat;
	
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

