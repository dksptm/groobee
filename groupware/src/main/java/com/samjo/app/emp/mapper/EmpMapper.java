package com.samjo.app.emp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.samjo.app.common.service.SearchVO;
import com.samjo.app.emp.service.EmpVO;

public interface EmpMapper {
	
	// 최초계정 등록.
	public int insertFirstEmp(EmpVO empVO);
	// 고객사 소속 계정 전체조회. 
	public List<EmpVO> selectEmpAll(@Param("cno") String custNo, @Param("sch") SearchVO search);
	public int countEmpAll(@Param("cno") String custNo, @Param("sch") SearchVO search);

}
