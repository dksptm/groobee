package com.samjo.app.project.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samjo.app.common.service.SearchVO;
import com.samjo.app.project.mapper.ProjectMapper;
import com.samjo.app.project.mapper.TaskMapper;
import com.samjo.app.project.service.ProjectVO;
import com.samjo.app.project.service.TaskEmpsVO;
import com.samjo.app.project.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService{
	ProjectMapper projectMapper;
	
	TaskMapper taskMapper;
	
	@Autowired
	public void ProjectServiceImpl(ProjectMapper projectMapper ,TaskMapper taskMapper) {
		this.projectMapper = projectMapper;
		this.taskMapper = taskMapper;
	}

	@Override // 프로젝트(하위)업무 전체조회
	public List<ProjectVO> taskAllList(SearchVO searchVO) {
		System.out.println("serchVO--->:" + searchVO);
		return taskMapper.selectTaskAllList(searchVO);
	}
	
	@Override // 프로젝트업무 페이징
	public int count(SearchVO searchVO) {
		return taskMapper.taskCount(searchVO);
	}
	
	@Override // 프로젝트(하위)업무 단건조회
	public ProjectVO taskInfo(int taskNo) {
		ProjectVO projectVO = new ProjectVO();
		projectVO = taskMapper.selectTask(taskNo);
		projectVO.setTaskEmps(taskMapper.selectTaskEmpList(taskNo));
		return projectVO;
	}
	
	@Override // 프로젝트(하위) 등록
	public int taskInsert(ProjectVO projectVO) {
		return taskMapper.insertTask(projectVO);
	}
	
	@Override // 업무참여자 수정 
	public Map<String, Object> taskOk(ProjectVO projectVO) {
		
		List<TaskEmpsVO> emps = projectVO.getTaskEmps();
		Map<String, Object> map = new HashMap<>();
		
		
		for(TaskEmpsVO emp : emps) {
			// task_emps u..
			taskMapper.updateTaskEmp(emp);
			System.out.println("emp.getResult()--------->"+emp.getResult());
			// task_common u (100)
			if(emp.getResult() == 100) {
				// prjt u
				System.out.println("프로젝트프로시저 시작--------------!");
				taskMapper.updatePrjt(projectVO);
			}
		}
		
		// 매퍼.xml에 추가한 프로시져 호출. 
		
		
		map.put("OUT", emps);
		return map;
	}
	
	@Override //프로젝트(하위)업무 수정
	public Map<String, Object> taskUpdate(ProjectVO projectVO) {
		Map<String, Object> map = new HashMap<>();
		boolean isSuccessed = false;
		int result = taskMapper.updateTask(projectVO);
		if (result == 1) {
			isSuccessed = true;
		}
		map.put("result", isSuccessed);
		map.put("target", projectVO);
		return map;
	}

	@Override // 프로젝트(하위) 업무 삭제
	public Map<String, Object> taskDelete(ProjectVO projectVO) {
		Map<String, Object> map = new HashMap<>();
		int result = taskMapper.deleteTask(projectVO.getTaskNo());
		//System.out.println("result:"+ result);
		if (result == 1) {
			map.put("taskNo", projectVO.getTaskNo());
		}
		return map;
	}

	@Override // 협력업체 조회
	public List<ProjectVO> CoopCoAllList() {
		return taskMapper.selectCoopCoAllList();
	}
	@Override // 협력업체 등록
	public int coopInsert(ProjectVO projectVO) {
		//System.out.println("협력업체:"+ projectVO);
		return taskMapper.insertCoop(projectVO);
	}
	@Override // 협력업체 단건
	public ProjectVO coopInfo(ProjectVO projectVO) {
		return taskMapper.selectCoop(projectVO);
	}
	@Override // 협력업체 수정
	public Map<String, Object> coopUpdate(ProjectVO projectVO) {
		Map<String, Object> map = new HashMap<>();
		boolean isSuccessed = false;
		int result = taskMapper.updateCoop(projectVO);
		if (result == 1) {
			isSuccessed = true;
		}
		map.put("result", isSuccessed);
		// js -> map.target = { coopCoNo : '', coName : '', ...};
		map.put("target", projectVO);
		return map;
	}
	@Override // 협력업체 삭제
	/*public int coopDelete(ProjectVO projectVO) {
		return projectMapper.deleteCoop(projectVO);
	}*/
	public Map<String, Object> coopDelete(ProjectVO projectVO) {
		Map<String, Object> map = new HashMap<>();
		int result = taskMapper.deleteCoop(projectVO.getCoopCoNo());
		//System.out.println("result:"+ result);
		if (result == 1) {
			map.put("coopCoNo", projectVO.getCoopCoNo());
		}
		return map;
	}

	
	
}
