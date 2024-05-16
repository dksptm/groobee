package com.samjo.app.project.service;

import java.util.List;
import java.util.Map;

public interface ProjectService {
	// 프로젝트 조회
	public List<ProjectVO> PrjtAllList();
	// 프로젝트 단건조회
	public ProjectVO prjtInfo(ProjectVO projectVO);
	// 프로젝트 등록
	public int prjtInsert(ProjectVO projectVO);
	// 프로젝트 수정
	//public int prjtupdate(ProjectVO projectVO);

	// 상시(주기적)업무 등록
	public int reguInsert(ProjectVO projectVO);
	// 상시(주기적)업무 조회
	public List<ProjectVO> reguAllList();
	
	
	// 협력업체 조회
	public List<ProjectVO> CoopCoAllList();
	// 협력업체 등록
	public int coopInsert(ProjectVO projectVO);
	// 협력업체 단건조회
	public ProjectVO coopInfo(ProjectVO projectVO);
	// 협력업체 수정
	public Map<String, Object> coopUpdate(ProjectVO projectVO);
	// 협력업체 삭제
	public Map<String, Object> coopDelete(ProjectVO projectVO);
	//public int coopDelete(ProjectVO projectVO);	
}
