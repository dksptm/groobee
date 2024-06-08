package com.samjo.app.emp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samjo.app.emp.mapper.DeptMapper;
import com.samjo.app.emp.service.DeptService;
import com.samjo.app.emp.service.DeptVO;
import com.samjo.app.emp.service.EmpVO;
import com.samjo.app.emp.service.JobVO;

@Service
public class DeptServiceImpl implements DeptService {
	
	DeptMapper deptMappr;

	@Autowired
	public DeptServiceImpl(DeptMapper deptMappr) {
		this.deptMappr = deptMappr;
	}

	@Override
	public List<DeptVO> myDeptEmps(EmpVO empVO) {
		return deptMappr.selectMyDept(empVO);
	}

	@Override
	public List<EmpVO> myCustEmps(String custNo) {
		return deptMappr.selectEmps(custNo);
	}

	
	
	@Override // 부서전체목록
	public List<DeptVO> deptAllList() {
		return deptMappr.selectDeptAll();
	}

	@Override // 프로젝트등록시 책임자 목록
	public List<EmpVO> respMngrList(String custNo) {
		return deptMappr.respMngrList(custNo);
	}

	@Override // 한 회사의 부서전체 목록
	public List<DeptVO> myCustDepts(EmpVO empVO) {
		return deptMappr.selectCustDeptAll(empVO);
	}

	@Override // 상시업무 등록시 책임자 목록
	public List<EmpVO> myDeptMngrs(EmpVO empVO) {
		return deptMappr.selectDeptMngr(empVO);
	}

	@Override // 한 회사의 직급전체 목록
	public List<JobVO> myCustJobs(EmpVO empVO) {
		return deptMappr.selectCustJobAll(empVO);
	}
	
	@Override	// 부서전체상세.
	public List<DeptVO> custDeptinfoAll(EmpVO empVO) {
		return deptMappr.selectCustDeptinfoAll(empVO);
	}
	
	@Override	// 부서이름 중복체크.
	public int dnameCheck(String deptName, String custNo) {
		return deptMappr.countDname(deptName, custNo);
	}

}
