package com.samjo.app.emp.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	@GetMapping("getDeptEmps")
	public String deptEmps(@RequestParam String deptId, Model model) {
		DeptVO findDept = deptService.myDeptEmps(deptId);
		model.addAttribute("dept", findDept);
		return "approval/doc/modal_aprs";
	}

	@GetMapping("getCustEmps")
	public String custEmps(@RequestParam String custNo, Model model) {
		List<EmpVO> emps = deptService.myCustEmps(custNo);
		model.addAttribute("emps", emps);
		return "approval/modal/modal_refs";
	}
	
}
