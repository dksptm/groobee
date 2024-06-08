package com.samjo.app.emp.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.samjo.app.common.util.SecuUtil;
import com.samjo.app.emp.service.DeptService;
import com.samjo.app.emp.service.DeptVO;
import com.samjo.app.emp.service.EmpVO;

@Controller
public class DeptController {
	
	DeptService deptService;

	@Autowired
	public DeptController(DeptService deptService) {
		this.deptService = deptService;
	}
	
	@GetMapping("cust/deptEmps")
	public String deptEmps(@RequestParam String deptId, Model model) {
		
		EmpVO empVO = SecuUtil.getLoginEmp();
		
		List<DeptVO> findDept = deptService.myDeptEmps(empVO);
		model.addAttribute("depts", findDept);
		return "approval/modal/modal_aprs";
	}

	@GetMapping("cust/custEmps")
	public String custEmps(@RequestParam String custNo, Model model) {
		List<EmpVO> emps = deptService.myCustEmps(custNo);
		model.addAttribute("emps", emps);
		return "approval/modal/modal_refs";
	}
	
	// 부서관리.
	@GetMapping("cust/manager/dept")
	public String custDeptinfoList(Model model) {
		EmpVO empVO = SecuUtil.getLoginEmp();
		
		if(empVO == null) {
			return "test/test";
		}
		
		if(!empVO.getPermId().equals("1C2c")) {
			return "test/test";
		}
		
		List<DeptVO> depts = deptService.custDeptinfoAll(empVO);
		model.addAttribute("depts", depts);
		
		return "manager/dept_list";
	}
	
	// 부서장급 사원가져오기 json.
	@ResponseBody
	@GetMapping("cust/manager/dept/mngrs")
	public List<EmpVO> getCustMngrList() {
		EmpVO empVO = SecuUtil.getLoginEmp();
		List<EmpVO> list = deptService.myDeptMngrs(empVO);
		return list;
	}
	
	// 부서이름체크.
	@ResponseBody
	@GetMapping("cust/manager/dnameCheck")
	public int idCheck(@RequestParam("dname") String deptName, @RequestParam("cno") String custNo) {
		int result = deptService.dnameCheck(deptName, custNo);
		return result;
	}
	
	
	

}
