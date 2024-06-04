package com.samjo.app.email.service;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
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
	
	private int fno;
	
	public EmailFileVO(String senEmailNo, String saveName, String uplName, String fileExt, Long fileSize, String uplEmp) {
		this.senEmailNo = senEmailNo;
		this.saveName = saveName;
		this.uplName = uplName;
		this.fileExt = fileExt;
		this.fileSize = fileSize;
		this.uplEmp = uplEmp;
	} 

}
