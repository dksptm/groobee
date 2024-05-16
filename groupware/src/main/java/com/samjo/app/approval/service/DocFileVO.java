package com.samjo.app.approval.service;

import java.util.Date;

import lombok.Data;

@Data
public class DocFileVO {
	
	private Integer fileNo;
	private Integer docNo;  
	private String saveName;
	private String uplName; 
	private String fileExt; 
	private Integer fileSize; 
	private Date fileDt;   
	private String uplEmp; 
	
}
