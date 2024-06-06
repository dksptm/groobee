package com.samjo.app.emp.service;

import java.util.List;
import java.util.Map;

import com.samjo.app.common.service.SearchVO;

public interface EmpService {
	
	// 최초계정 등록하기
	public Map<String, Object> insertFirstEmp(EmpVO empVO);
	// 고객사 소속 계정 전체조회
	public List<EmpVO> selectEmpAll(String custNo, SearchVO search);
	public int countEmpAll(String custNo, SearchVO search);

}
