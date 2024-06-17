package com.samjo.app.project.service;

import java.util.List;
import java.util.Map;

import com.samjo.app.common.service.SearchVO;
import com.samjo.app.emp.service.EmpVO;

public interface ProjectService {
	
	// 프로젝트 조회
	public List<ProjectVO> PrjtAllList(SearchVO searchVO, String custNo);
	// 프로젝트 업무 페이징
	public int count(SearchVO searchVO, String custNo);
	// 프로젝트 단순조회(taskInsert용)
	public List<ProjectVO> prjtList(SearchVO searchVO);
	
	//  공통업무VO List
	public List<ProjectVO> taskList(String prjtId, String custNo);
		
	// 프로젝트 단건조회
	public ProjectVO prjtInfo(String prjtId, String custNo);
	// 프로젝트 등록
	public int prjtInsert(ProjectVO projectVO);
	
	// 프로젝트 전체 수정
	public Map<String, Object> prjtModify(ProjectVO projectVO);
	
	// 효주 - 업무공통 간략조회.
	public List<ProjectVO> myCustTasks(EmpVO empVO);
	// 효주 끝.

	
}
