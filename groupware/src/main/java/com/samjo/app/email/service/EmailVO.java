package com.samjo.app.email.service;

import java.util.Date;

import lombok.Data;

@Data
public class EmailVO {
	
	// email TABLE
	private Integer senEmailNo;
	private String sender;
	private String title;
	private String cntn;
	private Date sentDt;
	private String emailStat;
	private Integer chainMailNo;
	private String custNo;
	
	// inbox TABLE
	private Integer recEmailNo;
	private Date recpDt;
	private String recp;
	private String recpType;
	private Date readDt;
	
	// email_file TABLE
	private Integer fileNo; // >> 다른 TABLE fileNo와 이름은 같으나, PRIMARY KEY이다. 여기서 다루는게 맞다
	private String saveName;
	private String uplName;
	private String fileExt;
	private String fileSize;
	private Date fileDt;
	private String uplEmp;
}
