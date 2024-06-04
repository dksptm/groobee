package com.samjo.app.project.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samjo.app.common.service.SearchVO;
import com.samjo.app.emp.service.EmpVO;
import com.samjo.app.project.mapper.ProjectMapper;
import com.samjo.app.project.mapper.TaskMapper;
import com.samjo.app.project.service.ProjectService;
import com.samjo.app.project.service.ProjectVO;


@Service
public class ProjectServiceImpl implements ProjectService {
	
	ProjectMapper projectMapper;
	
	TaskMapper taskMapper;
	
	@Autowired
	public ProjectServiceImpl(ProjectMapper projectMapper ,TaskMapper taskMapper) {
		this.projectMapper = projectMapper;
		this.taskMapper = taskMapper;
	}
	
	@Override // 프로젝트 전체조회
	public List<ProjectVO> PrjtAllList(SearchVO searchVO) {
		return projectMapper.selectPrjtAllList(searchVO);
	}
	
	@Override // 프로젝트 페이징
	public int count(SearchVO searchVO) {
		return projectMapper.prjtCount(searchVO);
	}
	
	@Override // 프로젝트 단순조회 taskInsert용
	public List<ProjectVO> prjtList(SearchVO searchVO) {
		return projectMapper.prjtList(searchVO);
	}
	
	@Override // 프로젝트 단건조회
	public ProjectVO prjtInfo(String prjtId) {
		return projectMapper.selectPrjt(prjtId);
	}
	
	@Override // 프로젝트 등록
	public int prjtInsert(ProjectVO projectVO) {
		System.out.println("프로젝트등록:"+ projectVO);
		return projectMapper.insertPrjt(projectVO);
	}
	
	@Override // 프로젝트 수정
	public Map<String, Object> prjtUpdate(ProjectVO projectVO) {
		Map<String, Object> map = new HashMap<>();
		boolean isSuccessed = false;
		int result = projectMapper.updatePrjt(projectVO);
		if (result == 1) {
			isSuccessed = true;
		}
		map.put("result", isSuccessed);
		map.put("target", projectVO);
		return map;
	}

	  @Override 
	  public void prjtDelete(String prjtId) {
	  projectMapper.deletePrjt(prjtId); 
	  }
	
	
	// 효주 - 업무간단조회
	@Override
	public List<ProjectVO> myCustTasks(String custNo) {
		return projectMapper.selectTasks(custNo);
	}
	// 효주 끝.

	
	@Override // 공통업무 List
	public List<ProjectVO> taskList(String prjtId, String custNo) {
		return projectMapper.taskList(prjtId, custNo);
	}


}
