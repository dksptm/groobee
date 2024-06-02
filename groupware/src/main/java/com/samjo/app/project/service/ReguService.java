package com.samjo.app.project.service;

import java.util.List;
import java.util.Map;

import com.samjo.app.common.service.SearchVO;
import com.samjo.app.emp.service.EmpVO;

public interface ReguService {
	
	// 처음 상시업무 등록(regu_stad + task_common + task_emps)
	public int reguStadInsert(ProjectVO regu);
	
	// 기존 상시업무 등록(task_common + task_emps)
	public int reguCommonInsert(ProjectVO regu);
	
	// 기존 상시업무 목록
	public List<ProjectVO> reguStadList(String custNo);
	
	// 단건조회
	public ProjectVO reguInfo(String custNo, Integer taskNo);
	
	// 담당자 업무완료 처리.
	public Map<String, Object> reguCmpltModify(List<TaskEmpsVO> emps);
	
	// 전체조회(상위)
	public List<ProjectVO> reguList(EmpVO empVO, SearchVO search);
	public int countRegus(EmpVO empVO, SearchVO search);
	// 단건조회(상위)
	public ProjectVO reguStadInfo(EmpVO empVO, String reguId);
	
	// 전체조회(하위)
	public List<ProjectVO> reguTaskList(String custNo, SearchVO search);
	public int countReguTasks(String custNo, SearchVO search);
	
}
