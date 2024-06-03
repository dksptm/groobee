package com.samjo.app.project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.samjo.app.common.service.SearchVO;
import com.samjo.app.emp.service.EmpVO;
import com.samjo.app.project.service.ProjectVO;
import com.samjo.app.project.service.TaskEmpsVO;

public interface TaskMapper {
	
		// 프로젝트 업무 조회
		public List<ProjectVO> selectTaskAllList(SearchVO searchVO);
		//프로젝트 업무 페이징
		public int taskCount(SearchVO searchVO);
		// 프로젝트 업무 등록
		public int insertTask(@Param("pj") ProjectVO projectVO);
		
		// 프로젝트 단건조회
		public ProjectVO selectTask(int taskNo);
		// 프로젝트 업무 참여자 조회
		public List<TaskEmpsVO> selectTaskEmpList(int taskNo);
		
		// 프로젝트 참여자 수정(프로시저)
		public void updateTaskEmp(TaskEmpsVO emp);
		// 업무모두완료시 -> 프로젝트완료상태 (프로시저)
		public void updatePrjt(ProjectVO projectVO);
		
		
		// 협력업체 조회
		public List<ProjectVO> selectCoopCoAllList(SearchVO searchVO);
		// 협력업체 페이징
		public int coCount(SearchVO searchVO);
		// 협력업체 등록
		public int insertCoop(ProjectVO projectVO);
		// 협력업체 단건
		public ProjectVO selectCoop(ProjectVO projectVO);
	
		// 프로젝트업무 단순조회
		public List<ProjectVO> taskList(EmpVO empVO);
		
		
		/*
		 * // 프로젝트 업무 수정 
		 * public int updateTask(ProjectVO projectVO); 
		 * // 프로젝트 업무 삭제
		 * public int deleteTask(@Param("taskNo")int taskNo);
		 */	
			/*
		 * // 협력업체 수정 
		 * public int updateCoop(ProjectVO projectVO); 
		 * // 협력업체 삭제 public int
		 * deleteCoop(@Param("coopCoNo")int coopCoNo);
		 */
}
