package com.samjo.app.project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.samjo.app.common.service.SearchVO;
import com.samjo.app.emp.service.EmpVO;
import com.samjo.app.project.service.ProjectVO;
import com.samjo.app.project.service.TaskEmpsVO;

public interface TaskMapper {
	
		// 업무 조회
		public List<ProjectVO> selectTaskAllList(SearchVO searchVO);
		// 업무 페이징
		public int taskCount(SearchVO searchVO);
		//  업무 등록
		public int insertTask(@Param("pj") ProjectVO projectVO);
		
		// 업무 단건조회
		public ProjectVO selectTask(int taskNo);
		// 업무 참여자 조회
		public List<TaskEmpsVO> selectTaskEmpList(int taskNo);
		
		// 업무 참여자 //
		// 업무 참여자 수정(프로시저)실행하기전 참여자의 참여여부를 초기화한다
		public void updateTaskEmpBefore(int taskNo);
		// 업무 참여자 수정(프로시저)
		public void updateTaskEmp(TaskEmpsVO emp);
		// 업무모두완료시 -> 프로젝트완료상태(프로시저)
		public void updatePrjt(ProjectVO projectVO);
		// 프로젝트 상황 업데이트 
		public ProjectVO upPrjt(ProjectVO projectVO);
		// 업무 상황 업데이트 
		public ProjectVO upTask(ProjectVO projectVO);
		
		// 프로젝트업무 단순조회
		public List<ProjectVO> taskList(EmpVO empVO);
		
		// 업무 전체 수정
		public int modifyTask(ProjectVO projectVO);
		
		// 협력업체 조회
		public List<ProjectVO> selectCoopCoAllList(SearchVO searchVO);
		// 협력업체 페이징
		public int coCount(SearchVO searchVO);
		// 협력업체 등록
		public int insertCoop(ProjectVO projectVO);
		// 협력업체 단건
		public ProjectVO selectCoop(ProjectVO projectVO);
		
		/*	 	
		  // 협력업체 수정 
		  public int updateCoop(ProjectVO projectVO); 
		  // 협력업체 삭제 public int
		  deleteCoop(@Param("coopCoNo")int coopCoNo);
		 */
}
