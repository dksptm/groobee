package com.samjo.app.common.util;

import org.springframework.security.core.context.SecurityContextHolder;

import com.samjo.app.emp.service.EmpVO;
import com.samjo.app.security.service.LoginUserVO;

public class SecuUtil {

	// 로그인 EmpVO 가져오는 함수.
	public static EmpVO getLoginEmp() {
		
		// principal 가져오기.
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		// EmpVO 생성.
		if (principal instanceof LoginUserVO) {
            LoginUserVO loginUserVO = (LoginUserVO) principal;
            
            String empId = loginUserVO.getEmpId();
            String custNo = loginUserVO.getCustNo();
            String deptId = loginUserVO.getDeptId();
            String permId = loginUserVO.getPermId();	
            
            EmpVO empVO = new EmpVO(empId, custNo, deptId, permId);
            return empVO;
            
        } else {
        	
        	return null;
        }
		
	}
	
	// EmpVO empVO = SecuUtil.getLoginEmp(); => 로그인정보 가져오는.
	
}
