package com.samjo.app.project.service;

import java.util.List;

public interface ReguService {
	
	// 처음 상시업무 등록(regu_stad + task_common + task_emps)
	public int reguStadInsert(ProjectVO regu);
	
	// 기존 상시업무 등록(task_common + task_emps)
	public int reguCommonInsert(ProjectVO regu);
	
	// 기존 상시업무 목록
	public List<ProjectVO> reguStadList(String custNo);
	
}
