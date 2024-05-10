package com.samjo.app.project.service;

import java.util.List;

public interface ProjectService {
	
	// 프로젝트 조회
	public List<ProjectVO> PrjtAllList();
	
	// 프로젝트 단건조회
	public ProjectVO prjtInfo(ProjectVO projectVO);
	
	// 프로젝트 등록
	public int prjtInsert(ProjectVO projectVO);
	
	// 프로젝트 수정
	public int prjtupdate(ProjectVO projectVO);



}
