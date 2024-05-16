package com.samjo.app.project.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	/*
	   @Override // 프로젝트 수정 
	   public int prjtupdate(ProjectVO projectVO) { 
	   return projectMapper.prjtUpdate(projectVO); 
	   }
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
	public List<ProjectVO> CoopCoAllList() {
		return projectMapper.selectCoopCoAllList();
	}
	
	@Override // 협력업체 등록
	public int coopInsert(ProjectVO projectVO) {
		System.out.println("협력업체:"+ projectVO);
		return projectMapper.insertCoop(projectVO);
	}
	
	@Override // 협력업체 단건
	public ProjectVO coopInfo(ProjectVO projectVO) {
		return projectMapper.selectCoop(projectVO);
	}
	
	@Override // 협력업체 수정
	public Map<String, Object> coopUpdate(ProjectVO projectVO) {
		// js -> let map = {}; 
		Map<String, Object> map = new HashMap<>();

		boolean isSuccessed = false;
		int result = projectMapper.updateCoop(projectVO);
		
		if (result == 1) {
			isSuccessed = true;
		}
		
		map.put("result", isSuccessed);
		// js -> map.target = { coopCoNo : '', coName : '', ...};
		map.put("target", projectVO);
		
		return map;
	}

	@Override // 협력업체 삭제
	/*public int coopDelete(ProjectVO projectVO) {
		return projectMapper.deleteCoop(projectVO);
	}*/
      public Map<String, Object> coopDelete(ProjectVO projectVO) {
		Map<String, Object> map = new HashMap<>();
		int result = projectMapper.deleteCoop(projectVO.getCoopCoNo());
		
		if (result == 1) {
			map.put("coopCoNo", projectVO.getCoopCoNo());
		}
		return map;
	}

}
