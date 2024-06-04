package com.samjo.app.emp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.samjo.app.emp.service.DeptVO;
import com.samjo.app.emp.service.EmpVO;

public interface DeptMapper {
	// 특정부서의 부서원 목록.
	public List<DeptVO> selectMyDept(@Param("emp") EmpVO empVO);
	// 한 회사의 모든 사원 목록.
	public List<EmpVO> selectEmps(@Param("cno")String custNo);
	
	// 부서 전체 목록 - 진경
	public List<DeptVO> selectDeptAll();
	// 총책임자 목록
	
	public List<EmpVO> respMngrList(@Param("cno")String custNo);
	
	// 한 회사의 부서전체 목록
	public List<DeptVO> selectCustDeptAll(@Param("emp") EmpVO empVO);
	// 관리자/부서장 가져오기(관리자:모든부서장,관리자 / 부서장:나의부서의 부서장만)
	public List<EmpVO> selectDeptMngr(@Param("emp") EmpVO empVO);
	
}
