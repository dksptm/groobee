package com.samjo.app.project.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samjo.app.common.service.SearchVO;
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
	public List<ProjectVO> PrjtAllList() {
		return projectMapper.selectPrjtAllList();
	}
	
	@Override // 프로젝트 단건조회
	public ProjectVO prjtInfo(ProjectVO projectVO) {
		return projectMapper.selectPrjt(projectVO);
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

	@Override // 프로젝트 삭제
	public Map<String, Object> prjtDelete(ProjectVO projectVO) {
		Map<String, Object> map = new HashMap<>();
		int prjtId = Integer.parseInt(projectVO.getPrjtId());
		int result = projectMapper.deletePrjt(prjtId);

		if (result == 1) {
			map.put("prjtId", projectVO.getPrjtId());
		}
		return map;
	}
	
	
	// 효주 - 업무간단조회
	@Override
	public List<ProjectVO> myCustTasks(String custNo) {
		return projectMapper.selectTasks(custNo);
	}
	// 효주 끝.


}
