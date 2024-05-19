package com.samjo.app.approval.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class PtoVO {
	
	private Integer docNo;
	private String ptoType;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date ptoStartDt;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date ptoEndDt;
	private String reason;

}
