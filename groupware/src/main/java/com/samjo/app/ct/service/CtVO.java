package com.samjo.app.ct.service;

import java.util.Date;

import lombok.Data;

@Data
public class CtVO {
	private int ctNo;
	private String custNo;
	private String custName;
	private String ctStat;
	private Date ctStartDt;
	private Date ctEndDt;
	private int ctAmt;
	private int useModCnt;
	private int maxEmps;
	private int curEmps;
	private String ctFile;
}
