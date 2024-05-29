package com.samjo.app.solmodule.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ModuleVO {

	private String modId;
	private String modName;
	private String modStat;
	private String modStatName;
	private String modInfo;
	
	//이용중 모듈데이터
	private int modHistNo;
	private int ctNo;
	private String custNo;
	private String custName;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date useStartDt;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date useEndDt;
	private String modUseStat;
	
}
