package com.samjo.app.project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.samjo.app.common.service.SearchVO;
import com.samjo.app.emp.service.EmpVO;
import com.samjo.app.project.service.ProjectVO;

public interface ProjectMapper {
	
	// 프로젝트 조회
	public List<ProjectVO> selectPrjtAllList(SearchVO searchVO);
	// 프로젝트 페이징
	public int prjtCount(SearchVO searchVO);
	// 프로젝트 단순조회 taskinsert용
	public List<ProjectVO> prjtList(SearchVO searchVO);
	
	// 프로젝트 단건조회
	public ProjectVO selectPrjt(String prjtId);
	// 프로젝트 등록
	public int insertPrjt(ProjectVO projectVO);
	
	// 프로젝트 수정
	public int updatePrjt(ProjectVO projectVO);
	// 프로젝트 삭제
	public int deletePrjt(@Param("prjtId")String prjtId);
	
	// 공통업무VO List
	public List<ProjectVO> taskList(@Param("pid")String prjtId, @Param("cno")String custNo);
	
	// 효주 - 업무공통 조회 : 마감날짜 이전 업무번호,부서ID,부서명,업무명.
	public List<ProjectVO> selectTasks(@Param("cno")String custNo);
	// 효주 끝.
	
}
