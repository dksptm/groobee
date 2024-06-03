package com.samjo.app.approval.service;

import java.util.Date;

import lombok.Data;

@Data
public class ExpVO {

	private Integer docNo;
	private Integer expSq;
	private Date expDt;
	private String vend;
	private Integer amt;

}
