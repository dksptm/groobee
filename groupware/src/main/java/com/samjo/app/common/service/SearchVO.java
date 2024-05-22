package com.samjo.app.common.service;

import java.util.List;

import lombok.Data;

@Data
public class SearchVO {
	private int page;
	private String searchCondition;
	private String keyword;
	
	// 전자결재 관련.
	private String docTitle;
	private List<Integer> schTaskNo;
	
}
