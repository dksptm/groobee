package com.samjo.app.pay.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class PayVO {
	private int payNo;
	private String custNo;
	private String custName;
	private int ctNo;
	private String payType;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date payCreDt;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date payExpcDt;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date payDueDt;
	private int servAmt;
	private String payHow;
	private String payStat;
	private String payStatName;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date payCmpltDt;
	private String payer;
	private int payAmt;
	private int overdue;
	private String remk;
}
