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
	public int updatePrjt(ProjectVO projectVO);
	// 프로젝트 삭제
	public int deletePrjt(@Param("prjtId")int prjtId);
	
	// 효주 - 업무공통 조회 : 마감날짜 이전 업무번호,부서ID,부서명,업무명.
	public List<ProjectVO> selectTasks(@Param("cno")String custNo);
	// 효주 끝.
	
}
