package com.samjo.app.project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samjo.app.project.mapper.ProjectMapper;
import com.samjo.app.project.service.ProjectService;
import com.samjo.app.project.service.ProjectVO;

@Service
public class ProjectServiceImpl implements ProjectService {
	
	
	ProjectMapper projectMapper;
	
	@Autowired
	public ProjectServiceImpl(ProjectMapper projectMapper) {
		this.projectMapper = projectMapper;
	}
	
	// 프로젝트 전체조회
	@Override
	public List<ProjectVO> PrjtAllList() {
		return projectMapper.selectPrjtAllList();
	}
	
	// 프로젝트 단건조회
	@Override
	public ProjectVO prjtInfo(ProjectVO projectVO) {
		return projectMapper.selectPrjt(projectVO);
	}
	
	// 프로젝트 등록
	@Override
	public int prjtInsert(ProjectVO projectVO) {
		return projectMapper.prjtInsert(projectVO);
	}
	
	// 프로젝트 수정
	@Override
	public int prjtupdate(ProjectVO projectVO) {
		return projectMapper.prjtUpdate(projectVO);
	}

}
