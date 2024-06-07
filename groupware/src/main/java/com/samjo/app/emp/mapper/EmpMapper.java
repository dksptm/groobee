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
	
	// id check
	public int countEno(@Param("eno") String empNo, @Param("cno") String custNo);
	// 사원등록.
	public int insertEmp(EmpVO empVO);
	// 사원 단건조회.
	public EmpVO selectEmpInfo(@Param("eid") String empId, @Param("cno") String custNo);
	// 사원수정.
	public int updateEmp(@Param("emp") EmpVO empVO);
	

}
