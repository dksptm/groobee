package com.samjo.app.emp.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.samjo.app.emp.service.EmpService;
import com.samjo.app.emp.service.EmpVO;

@Controller
public class EmpController {
	
	@Autowired
	EmpService empService;
	
	@ResponseBody
	@PostMapping("sol/customer/firstEmp")
	private Map<String, Object> insertFirstEmp(@RequestBody EmpVO empVO) {
		System.out.println(empVO);
		return empService.insertFirstEmp(empVO);
	}
	
}
