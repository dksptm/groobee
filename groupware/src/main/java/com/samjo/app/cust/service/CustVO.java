package com.samjo.app.cust.service;

import java.util.List;

import com.samjo.app.ct.service.CtVO;

import lombok.Data;

@Data
public class CustVO {
	private String custNo;
	private String brn;
	private String custName;
	private String rep;
	private String custAddr;
	private String custTel;
	private String payPic;
	private String payPicTel;
	
	private Integer lastCtNo;
	private String lastCtStat;
	private String lastCtStatName;
	
	private Integer curEmps;
	private Integer maxEmps;
	
	private String spell;
	private List<CtVO> ctList;
}
