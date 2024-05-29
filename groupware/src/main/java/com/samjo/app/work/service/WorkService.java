package com.samjo.app.work.service;

import java.util.List;
import java.util.Map;


public interface WorkService {

	// 전체 페이지 수
	public int workcount(WorkSearchVO worksearchVO);
	public int managercount();
	
	// 근태 전체 조회
	public List<WorkVO> workList(WorkSearchVO worksearchVO);
	
	// 출근버튼
	public int workin();
	
	// 퇴근버튼
	public int workout();
	
	// 출근등록
	public int insertWork(WorkVO workVo);
	
	// 관리자 페이지 전체조회
	public List<WorkManagerVO> managerWorkList(WorkManagerSearchVO workmanagersearchVO);
	
	// 전체페이지의 상세 조회
	public WorkVO selectWork(WorkVO workVO);
	
	// 전체페이지의 수정페이지
	//public WorkVO updateWork(WorkVO workVO);
	// 수정 처리
	public Map<String, Object> update(WorkVO workVO);
}