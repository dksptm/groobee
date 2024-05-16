package com.samjo.app.emp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.samjo.app.emp.service.DeptVO;
import com.samjo.app.emp.service.EmpVO;

public interface DeptMapper {
	// 특정부서의 부서원 목록.
	public DeptVO selectMyDept(@Param("did")String deptId);
	// 한 회사의 모든 사원 목록.
	public List<EmpVO> selectEmps(@Param("cno")String custNo);
}
