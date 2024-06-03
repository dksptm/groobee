package com.samjo.app.work.service;

import java.util.List;
import java.util.Map;


public interface WorkService {

	// 전체 페이지 수
	public int workcount(WorkSearchVO worksearchVO);
	public int managercount();

	// 근태 전체기록
	public List<WorkVO> selectlist(WorkSearchVO worksearchVO);
	
	// 출근버튼
	public Map<String, Object> workin(WorkVO workVo);
	
	// 퇴근버튼
	public Map<String, Object> workout(WorkVO workVo);
	
	// 조퇴버튼
	public Map<String, Object> workstop(WorkVO workVo);
	
	// 관리자 페이지 전체조회
	public List<WorkManagerVO> managerWorkList(WorkManagerSearchVO workmanagersearchVO);
	
	// 관리자의 선택 조회
	public List<WorkVO> workList(WorkSearchVO worksearchVO);
	
	// 전체페이지의 상세 조회
	public WorkVO selectWork(WorkVO workVO);
	
	// 수정 처리
	public Map<String, Object> update(WorkVO workVO);
	
	// 계정 정보 조회
	public WorkVO selectemp(WorkVO workVO);
}