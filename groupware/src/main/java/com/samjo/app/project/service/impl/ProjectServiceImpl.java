package com.samjo.app.project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samjo.app.project.mapper.ProjectMapper;
import com.samjo.app.project.service.CoopCoVO;
import com.samjo.app.project.service.ProjectService;
import com.samjo.app.project.service.ProjectVO;


@Service
public class ProjectServiceImpl implements ProjectService {
	
	ProjectMapper projectMapper;
	
	@Autowired
	public ProjectServiceImpl(ProjectMapper projectMapper) {
		this.projectMapper = projectMapper;
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
		return projectMapper.prjtInsert(projectVO);
	}
	/*
	 * @Override // 프로젝트 수정 public int prjtupdate(ProjectVO projectVO) { return
	 * projectMapper.prjtUpdate(projectVO); }
	 */
	

	@Override // 상시(주기적)업무 등록
	public int reguInsert(ProjectVO projectVO) {
		return projectMapper.reguInsert(projectVO);
	}
	@Override // 상시(주기적)업무 조회
	public List<ProjectVO> reguAllList() {
		return projectMapper.selectReguAllList();
	}
	
	
	
	
	@Override // 협력업체 조회
	public List<CoopCoVO> CoopCoAllList() {
		return projectMapper.selectCoopCoAllList();
	}
	@Override // 협력업체 등록
	public int coopInsert(CoopCoVO coopCoVO) {
		return projectMapper.coopInsert(coopCoVO);
	}

	@Override
	public CoopCoVO coopInfo(CoopCoVO coopCoVO) {
		return projectMapper.selectCoop(coopCoVO);
	}


	
}
