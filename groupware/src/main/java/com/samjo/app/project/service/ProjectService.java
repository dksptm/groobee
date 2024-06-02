package com.samjo.app.project.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.samjo.app.common.service.SearchVO;

public interface ProjectService {
	
	// 프로젝트 조회
	public List<ProjectVO> PrjtAllList(SearchVO searchVO);
	// 프로젝트 업무 페이징
	public int count(SearchVO searchVO);
	
	// 프로젝트 단순조회(taskInsert용)
	public List<ProjectVO> prjtList(SearchVO searchVO);
	
	// 프로젝트 단건조회
	public ProjectVO prjtInfo(String prjtId);
	// 프로젝트 등록
	public int prjtInsert(ProjectVO projectVO);
	// 프로젝트 수정
	public Map<String, Object> prjtUpdate(ProjectVO projectVO);
	// 프로젝트 삭제
	public void prjtDelete(String prjtId);
	
	// 효주 - 업무공통 간략조회.
	public List<ProjectVO> myCustTasks(String custNo);
	// 효주 끝.
	
	

	
	
}
