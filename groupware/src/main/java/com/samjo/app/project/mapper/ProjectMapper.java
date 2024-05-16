package com.samjo.app.project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.samjo.app.project.service.ProjectVO;

public interface ProjectMapper {

	// 프로젝트 조회
	public List<ProjectVO> selectPrjtAllList();
	// 프로젝트 단건조회
	public ProjectVO selectPrjt(ProjectVO projectVO);
	// 프로젝트 등록
	public int insertPrjt(ProjectVO projectVO);
	// 프로젝트 수정
	//public int prjtUpdate(ProjectVO projectVO);
	// 프로젝트 삭제
	// public int deletePrjt(@Param()int prjtId);

	// 상시(주기적) 업무 등록
	public int reguInsert(ProjectVO projectVO);
	// 상시(주기적) 업무 조회
	public List<ProjectVO> selectReguAllList();

	// 협력업체 조회
	public List<ProjectVO> selectCoopCoAllList();
	// 협력업체 등록
	public int insertCoop(ProjectVO projectVO);
	// 협력업체 단건
	public ProjectVO selectCoop(ProjectVO projectVO);
	// 협력업체 수정
	public int updateCoop(ProjectVO projectVO);
	// 협력업체 삭제
	 public int deleteCoop(@Param("cNo")int coopCoNo);

}
