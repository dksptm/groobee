package com.samjo.app.emp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.samjo.app.emp.service.DeptVO;
import com.samjo.app.emp.service.EmpVO;
import com.samjo.app.emp.service.JobVO;

public interface DeptMapper {
	// 특정부서의 부서원 목록.
	public List<DeptVO> selectMyDept(@Param("emp") EmpVO empVO);
	// 한 회사의 모든 사원 목록.
	public List<EmpVO> selectEmps(@Param("cno")String custNo);
	
	// 부서 전체 목록 - 진경
	public List<DeptVO> selectDeptAll();
	// 총책임자 목록
	
	public List<EmpVO> respMngrList(@Param("cno")String custNo);
	
	// 한 회사의 부서전체 목록, 직급전체 목록.
	public List<DeptVO> selectCustDeptAll(@Param("emp") EmpVO empVO);
	public List<JobVO> selectCustJobAll(@Param("emp") EmpVO empVO);
	// 관리자/부서장 가져오기(관리자:모든부서장,관리자 / 부서장:나의부서의 부서장만)
	public List<EmpVO> selectDeptMngr(@Param("emp") EmpVO empVO);
	
	// 최초계정 등록시 필요한 해당회사 부서 최초등록.
	public int insertFirstDept(EmpVO empVO);
	// 최초계정 등록시 해당회사의 최초직급 등록.
	public int insertFirstJob(EmpVO empVO);
	// 현재 계약중인 계약번호 가져오기.
	public Integer selectCurCtNo(String custNo);
	
	// 한 회사의 부서전체 목록(상세목록)
	public List<DeptVO> selectCustDeptinfoAll(@Param("emp") EmpVO empVO);
	
	// 부서이름 중복체크
	public int countDname(@Param("dname") String deptName, @Param("cno") String custNo);
	
	// 부서등록.
	public int insertDeptInfo(DeptVO dept);
	// 부서장 null.
	public int updateDeptMngr(DeptVO dept);
	
	// 부서수정
	public int updateDeptInfo(DeptVO dept);
	
	// 부서삭제
	public int deleteDeptInfo(@Param("did") String deptId, @Param("cno") String custNo);
	
	
	
}
