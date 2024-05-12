package com.samjo.app.emp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.samjo.app.emp.service.DeptService;
import com.samjo.app.emp.service.DeptVO;

@Controller
public class DeptController {
	
	DeptService deptService;

	@Autowired
	public DeptController(DeptService deptService) {
		this.deptService = deptService;
	}
	
	@GetMapping("deptTest")
	public String deptTest(@RequestParam String deptId, Model model) {
		DeptVO findDept = deptService.myDeptEmps(deptId);
		model.addAttribute("dept", findDept);
		System.out.println(findDept);
		return "approval/doc/modal";
	}

}
