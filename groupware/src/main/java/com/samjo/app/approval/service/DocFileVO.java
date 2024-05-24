package com.samjo.app.approval.service;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class DocFileVO {
	
	private Integer fileNo;
	private Integer docNo;  
	private String saveName;
	private String uplName; 
	private String fileExt; 
	private Long fileSize; 
	private Date fileDt;   
	private String uplEmp;
	
	public DocFileVO(Integer docNo, String saveName, String uplName, String fileExt, Long fileSize, String uplEmp) {
		this.docNo = docNo;
		this.saveName = saveName;
		this.uplName = uplName;
		this.fileExt = fileExt;
		this.fileSize = fileSize;
		this.uplEmp = uplEmp;
	} 
	
	

}
