package com.samjo.app.project.mapper;

import java.util.List;

import com.samjo.app.project.service.CoopCoVO;
import com.samjo.app.project.service.ProjectVO;

public interface ProjectMapper {

	// 프로젝트 조회
	public List<ProjectVO> selectPrjtAllList();
	
	// 프로젝트 단건조회
	public ProjectVO selectPrjt(ProjectVO projectVO);
	
	// 프로젝트 등록
	public int prjtInsert(ProjectVO projectVO);
	
	// 프로젝트 수정
	public int prjtUpdate(ProjectVO projectVO);
	
	// 프로젝트 삭제
	//public int deletePrjt(@Param()int prjtId);
	
	
	// 협력업체 등록
	public List<CoopCoVO> selectCoopAllList();
	
	// 협력업체 수정
	public int coopCoUpdate(CoopCoVO coopCoVO);


	
	// 협력업체 삭제
	//public int deleteCoopCo(@Param()int coopCoNo);
	
	
}
