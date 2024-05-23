package com.samjo.app.common.service;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class SearchVO {
	private int page;
	private String searchCondition;
	private String keyword;
	
	// 전자결재 관련.
	private String docTitle;
	private List<Integer> schTaskNo;
	
	// 계약 관련.
	private String ctName;
	private String ctStat;
	private String ctSort;
	private String ctNo;
	private String custNo;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date ctStart;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date ctEnd;
	
}
