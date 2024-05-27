package com.samjo.app.project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.samjo.app.common.service.SearchVO;
import com.samjo.app.project.service.ProjectVO;

public interface ProjectMapper {
	// 프로젝트 상위 //
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
	
	// 프로젝트 하위 - 업무 //
	// 프로젝트 업무 조회
	public List<ProjectVO> selectTaskAllList(SearchVO searchVO);
	//프로젝트 업무 페이징
	public int taskCount(SearchVO searchVO);
	// 프로젝트 업무 등록
	public int insertTask(@Param("pj") ProjectVO projectVO);
	// 프로젝트 단건조회
	public ProjectVO selectTask(ProjectVO projectVO);
	// 프로젝트 업무 수정
	public int updateTask(ProjectVO projectVO);
	// 프로젝트 업무 삭제
	public int deleteTask(@Param("taskNo")int taskNo);
	
	// 상시업무(주기적) //
	// 상시 업무 조회
	public List<ProjectVO> selectReguAllList();
	// 상시 업무 단건조회
	public ProjectVO selectRegu(ProjectVO projectVO);
	// 상시 업무 등록
	public int reguInsert(ProjectVO projectVO);
	// 상시 업무 수정
	public int updateRegu(ProjectVO projectVO);
	// 상시 업무 삭제
	public int deleteRegu(@Param("reguId")int reguId);
	
	// 협력업체 조회
	public List<ProjectVO> selectCoopCoAllList();
	// 협력업체 단건
	public ProjectVO selectCoop(ProjectVO projectVO);
	// 협력업체 등록
	public int insertCoop(ProjectVO projectVO);
	// 협력업체 수정
	public int updateCoop(ProjectVO projectVO);
	// 협력업체 삭제
	 public int deleteCoop(@Param("coopCoNo")int coopCoNo);
	 
	// 효주 - 업무공통 조회 : 마감날짜 이전 업무번호,부서ID,부서명,업무명.
	public List<ProjectVO> selectTasks(@Param("cno")String custNo);
	// 효주 끝.
	
	

}
