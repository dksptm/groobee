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
	// public int deletePrjt(@Param()int prjtId);

	// 상시(주기적) 업무 등록
	public int reguInsert(ProjectVO projectVO);
	// 상시(주기적) 업무 조회
	public List<ProjectVO> selectReguAllList();

	// 협력업체 조회
	public List<CoopCoVO> selectCoopCoAllList();
	// 협력업체 등록
	public int coopInsert(CoopCoVO coopCoVO);
	// 협력업체 단건
	public CoopCoVO selectCoop(CoopCoVO coopCoVO);

	// 협력업체 수정
	// public int coopCoUpdate(CoopCoVO coopCoVO);
	// 협력업체 삭제
	// public int deleteCoopCo(@Param()int coopCoNo);

}
