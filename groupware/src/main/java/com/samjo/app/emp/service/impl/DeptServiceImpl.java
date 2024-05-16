package com.samjo.app.emp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samjo.app.emp.mapper.DeptMapper;
import com.samjo.app.emp.service.DeptService;
import com.samjo.app.emp.service.DeptVO;
import com.samjo.app.emp.service.EmpVO;

@Service
public class DeptServiceImpl implements DeptService {
	
	DeptMapper deptMappr;

	@Autowired
	public DeptServiceImpl(DeptMapper deptMappr) {
		this.deptMappr = deptMappr;
	}

	@Override
	public DeptVO myDeptEmps(String deptId) {
		return deptMappr.selectMyDept(deptId);
	}

	@Override
	public List<EmpVO> myCustEmps(String custNo) {
		return deptMappr.selectEmps(custNo);
	}

}
