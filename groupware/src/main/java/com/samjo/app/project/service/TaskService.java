package com.samjo.app.project.service;

import java.util.List;
import java.util.Map;

import com.samjo.app.common.service.SearchVO;
import com.samjo.app.emp.service.EmpVO;

public interface TaskService {
	// 프로젝트 하위 - (프로젝트)업무 //
	//  업무 조회 
	public List<ProjectVO> taskAllList(SearchVO searchVO);
	//  업무 페이징
	public int count(SearchVO searchVO);
	//  업무 단건조회
	public ProjectVO taskInfo(int taskNo);
	//  업무 등록
	public int taskInsert(ProjectVO projectVO);
	//  업무 참여자 수정 
	public Map<String, Object> taskOk(ProjectVO projectVO);
	
	// 업무 단순조회
	public List<ProjectVO> taskList(EmpVO empVO);
	
	// 업무 전체 수정
	public Map<String, Object> taskModify(ProjectVO projectVO);
	
	// 협력업체 조회
	public List<ProjectVO> CoopCoAllList(SearchVO searchVO);
	// 협력업체 페이징
	public int coCount(SearchVO searchVO);
	// 협력업체 등록
	public int coopInsert(ProjectVO projectVO);
	// 협력업체 단건조회
	public ProjectVO coopInfo(ProjectVO projectVO);
	
	

	
	/*
	
	// 협력업체 수정
		public Map<String, Object> coopUpdate(ProjectVO projectVO);
	 */
}
