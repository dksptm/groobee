package com.samjo.app.emp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.samjo.app.common.service.SearchVO;
import com.samjo.app.emp.mapper.DeptMapper;
import com.samjo.app.emp.mapper.EmpMapper;
import com.samjo.app.emp.service.DeptVO;
import com.samjo.app.emp.service.EmpService;
import com.samjo.app.emp.service.EmpVO;
import com.samjo.app.emp.service.JobVO;

@Service
public class EmpServiceImpl implements EmpService {

	@Autowired
	EmpMapper empMapper;
	
	@Autowired
	DeptMapper deptMapper;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	// 최초계정 생성
	@Transactional
	@Override
	public Map<String, Object> insertFirstEmp(EmpVO empVO) {
		
		Map<String, Object> map = new HashMap<>();
		
		// 계약번호 가져오기
		System.out.println(empVO.getCustNo());
		Integer ctno = deptMapper.selectCurCtNo(empVO.getCustNo());
		
		if(ctno == null) {
			map.put("result", "계약번호 없음");
			return map;
		} else {
			empVO.setCtNo(ctno);
		}
		
		int result = 0;
		// 부서등록, 직급등록.
		result += deptMapper.insertFirstDept(empVO);
		result += deptMapper.insertFirstJob(empVO);
		
		String pw = passwordEncoder.encode("1111"); // 최초 비번은 1111.
		empVO.setPw(pw);
		
		result += empMapper.insertFirstEmp(empVO);
		
		if(result == 3) {
			map.put("result", "최초계정 등록 완료");
		} else {
			map.put("result", "DB 확인 필요");			
		}
		
		return map;
	}

	// 고객사 소속 계정 전체조회.
	@Override
	public List<EmpVO> selectEmpAll(String custNo, SearchVO search) {
		return empMapper.selectEmpAll(custNo, search);
	}

	@Override
	public int countEmpAll(String custNo, SearchVO search) {
		return empMapper.countEmpAll(custNo, search);
	}
	
	// 사원등록 위한 부서조회
	@Override
	public List<DeptVO> insertFormDepts(EmpVO empVO) {
		return deptMapper.selectCustDeptAll(empVO);
	}
	// 사원등록 위한 직급조회
	@Override
	public List<JobVO> insertFormJobs(EmpVO empVO) {
		return deptMapper.selectCustJobAll(empVO);
	}
	// 사원등록 위한 사번중복체크
	@Override
	public int idCheck(String eno, String cno) {
		return empMapper.countEno(eno, cno);
	}
	
	// 사원등록.
	@Override
	public String insertEmp(EmpVO empVO) {
		
		String pw = empVO.getEmpNo();
		pw = passwordEncoder.encode(pw);
		empVO.setPw(pw);
		
		String eid = "";
		int result = 0;
		
		result = empMapper.insertEmp(empVO);
		if(result == 1) {
			eid = empVO.getEmpId();
		}
		
		return eid;
	}
	
	// 사원단건조회.
	@Override
	public EmpVO selectEmpInfo(String empId, String custNo) {
		return empMapper.selectEmpInfo(empId, custNo);
	}
	
	// 사원수정.
	@Override
	public String updateEmp(EmpVO empVO) {
		
		if(empVO.getPw() != null && !empVO.getPw().equals("")) {
			String pw = empVO.getPw();
			pw = passwordEncoder.encode(pw);
			empVO.setPw(pw);
		}
		
		String eid = "NG";
		int result = 0;
		
		result = empMapper.updateEmp(empVO);
		if(result == 1) {
			eid = empVO.getEmpId();
		}
		
		return eid;
	}


}
