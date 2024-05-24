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
	private List<String> schTaskName;
	
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
	
	
	// 이메일 관련 inboxList, emailList, wastedList 전부 공유
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date sendDt;
	
	private String emTitle;
	private String sender;
	private boolean hasFile; //첨부파일 유무 -> 있으면 클립이미지
	
}
