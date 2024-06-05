package com.samjo.app.emp.service;

import java.util.Map;

public interface EmpService {
	
	// 최초계정 등록하기
	public Map<String, Object> insertFirstEmp(EmpVO empVO);

}
