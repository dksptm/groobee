package com.samjo.app.ct.service;

import java.util.Date;
import java.util.List;

import com.samjo.app.emp.service.EmpVO;
import com.samjo.app.solmodule.service.ModuleVO;

import lombok.Data;

@Data
public class CtVO {
	private int ctNo;
	private String custNo;
	private String custName;
	private String ctStat;
	private String ctStatName;
	private Date ctStartDt;
	private Date ctEndDt;
	private int ctAmt;
	private int useModCnt;
	private int maxEmps;
	private int curEmps;
	private String ctFile;
	
	private String brn;
	private String rep;
	
	//상세정보 - 이용중 모듈목록
	private List<ModuleVO> modList;
	//상세정보 - 현재 사용중계정목록
	private List<EmpVO> empList;
	//상세정보 - 계약변경이력
	
}
