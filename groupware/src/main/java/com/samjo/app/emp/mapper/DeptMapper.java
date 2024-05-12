package com.samjo.app.emp.mapper;

import org.apache.ibatis.annotations.Param;

import com.samjo.app.emp.service.DeptVO;

public interface DeptMapper {
	// 특정부서의 부서원 목록.
	public DeptVO selectMyDept(@Param("did")String deptId);
}
