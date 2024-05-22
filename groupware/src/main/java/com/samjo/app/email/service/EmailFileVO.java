package com.samjo.app.email.service;

import java.util.Date;

import lombok.Data;

@Data
public class EmailFileVO {
	
	private Integer fileNo;
	private String senEmailNo;  
	private String saveName;
	private String uplName; 
	private String fileExt; 
	private Long fileSize; 
	private Date fileDt;   
	private String uplEmp; 

}
