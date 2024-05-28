package com.samjo.app.project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.samjo.app.project.service.ProjectVO;

public interface ReguMapper {
	
	// 상시업무 상위등록(regu_stad)
	public int insertRegu(ProjectVO regu);
	
	// 상시업무 하위등록(task_common)
	public int insertReguTaskEmp(ProjectVO regu);
	
	// 기존 상시업무 목록
	public List<ProjectVO> selectReguAll(@Param("cno") String custNo);
	
}
