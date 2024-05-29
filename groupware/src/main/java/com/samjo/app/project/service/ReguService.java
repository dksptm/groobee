package com.samjo.app.project.service;

import java.util.List;
import java.util.Map;

public interface ReguService {
	
	// 처음 상시업무 등록(regu_stad + task_common + task_emps)
	public int reguStadInsert(ProjectVO regu);
	
	// 기존 상시업무 등록(task_common + task_emps)
	public int reguCommonInsert(ProjectVO regu);
	
	// 기존 상시업무 목록
	public List<ProjectVO> reguStadList(String custNo);
	
	// 단건조회
	public ProjectVO reguInfo(String custNo, Integer taskNo);
	
	// 담당자 업무완료.
	public Map<String, Object> reguCmpltModify(List<TaskEmpsVO> emps);
	
}
