package com.samjo.app.emp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.samjo.app.emp.mapper.DeptMapper;
import com.samjo.app.emp.mapper.EmpMapper;
import com.samjo.app.emp.service.DeptService;
import com.samjo.app.emp.service.DeptVO;
import com.samjo.app.emp.service.EmpVO;
import com.samjo.app.emp.service.JobVO;

@Service
public class DeptServiceImpl implements DeptService {
	
	DeptMapper deptMapper;
	EmpMapper empMapper;

	@Autowired
	public DeptServiceImpl(DeptMapper deptMapper, EmpMapper empMapper) {
		this.deptMapper = deptMapper;
		this.empMapper = empMapper;
	}

	@Override
	public List<DeptVO> myDeptEmps(EmpVO empVO) {
		return deptMapper.selectMyDept(empVO);
	}

	@Override
	public List<EmpVO> myCustEmps(String custNo) {
		return deptMapper.selectEmps(custNo);
	}

	@Override // 부서전체목록
	public List<DeptVO> deptAllList() {
		return deptMapper.selectDeptAll();
	}

	@Override // 프로젝트등록시 책임자 목록
	public List<EmpVO> respMngrList(String custNo) {
		return deptMapper.respMngrList(custNo);
	}

	@Override // 한 회사의 부서전체 목록
	public List<DeptVO> myCustDepts(EmpVO empVO) {
		return deptMapper.selectCustDeptAll(empVO);
	}

	@Override // 상시업무 등록시 책임자 목록
	public List<EmpVO> myDeptMngrs(EmpVO empVO) {
		return deptMapper.selectDeptMngr(empVO);
	}

	@Override // 한 회사의 직급전체 목록
	public List<JobVO> myCustJobs(EmpVO empVO) {
		return deptMapper.selectCustJobAll(empVO);
	}
	
	@Override	// 부서전체상세.
	public List<DeptVO> custDeptinfoAll(EmpVO empVO) {
		return deptMapper.selectCustDeptinfoAll(empVO);
	}
	
	@Override	// 부서이름 중복체크.
	public int dnameCheck(String deptName, String custNo) {
		return deptMapper.countDname(deptName, custNo);
	}
	
	// 부서등록
	@Transactional
	@Override
	public String insertDeptInfo(DeptVO dept) {
		if(deptMapper.insertDeptInfo(dept) == 1) {
			
			if(dept.isMngrFlag()) {
				deptMapper.updateDeptMngr(dept);				
			}
			
			EmpVO emp = new EmpVO();
			emp.setDeptId(dept.getDeptId());
			emp.setEmpId(dept.getDeptMngr());
			empMapper.updateEmp(emp);
			
			return "OK";
		}
		
		return "NG";
	}
	
	// 부서수정.
	@Transactional
	@Override
	public String updateDeptInfo(DeptVO dept) {
		if(deptMapper.updateDeptInfo(dept) == 1) {
			
			if(dept.isMngrFlag()) {
				deptMapper.updateDeptMngr(dept);				
			}
			
			EmpVO emp = new EmpVO();
			emp.setDeptId(dept.getDeptId());
			emp.setEmpId(dept.getDeptMngr());
			empMapper.updateEmp(emp);
			
			return "OK";
		}
		
		return "NG";
	}
	
	
	// 부서삭제
	@Override
	public int deleteDeptInfo(String[] deptIdList, String custNo) {
		int result = 0;
		if(deptIdList.length == 0) {
			return 0;
		}
		for(String did : deptIdList) {
			result += deptMapper.deleteDeptInfo(did, custNo);
		}
		return result;
	}

}
