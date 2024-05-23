package com.samjo.app.common.util;

import org.springframework.security.core.context.SecurityContextHolder;

import com.samjo.app.emp.service.EmpVO;
import com.samjo.app.security.service.LoginUserVO;


public class SecuUtil {

	// 로그인 사용자 가져오기.
	public static EmpVO getLoginEmp() {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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
