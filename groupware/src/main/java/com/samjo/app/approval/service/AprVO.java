package com.samjo.app.approval.service;

import java.util.Date;

import lombok.Data;

@Data
public class AprVO {
	private Integer aprNo;   
	private Integer docNo;    
	private String custNo;
	private Integer aprSq;      
	private Integer aprLine;       
	private String aprEmp;    
	private String aprYn;          
	private Date aprDt;                  
	private String reCmt;            
	private String aprName;  
	
	// code name.
	private String aprCode;
	
	// 프로시저 결과반환.
	private String result;
}
