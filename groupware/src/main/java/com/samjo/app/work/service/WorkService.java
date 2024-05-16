package com.samjo.app.work.service;

import java.util.List;

public interface WorkService {

	// 근태 전체 조회
	public List<WorkVO> workList();
	
	// 전체 페이지 수
	public int workcount();
	
	// 조회 조건들
	public WorkPageDTO page(WorkPageDTO workPageDTO); 
	

	
}
