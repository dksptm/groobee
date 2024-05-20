package com.samjo.app.work.service;

import java.util.List;

public interface WorkService {

	// 전체 페이지 수
	public int workcount();
	
	// 근태 전체 조회
	public List<WorkVO> workList(WorkSearchVO worksearchVO);
	
	// 출근버튼
	public int workin();
	
	// 퇴근버튼
	public int workout();
	
	// 출근등록
	public int insertWork();
	
	// 관리자 페이지 전체조회
	public List<WorkVO> managerWorkList(WorkSearchVO worksearchVO);
	
	// 관리자 페이지 상세 조회
	public WorkVO selectWork(WorkVO workVO);
	
	// 관리자 페이지 수정
	public int updateWork(WorkVO workVO);
}
