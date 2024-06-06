package com.samjo.app.emp.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.samjo.app.common.service.PageDTO;
import com.samjo.app.common.service.SearchVO;
import com.samjo.app.emp.service.EmpService;
import com.samjo.app.emp.service.EmpVO;

@Controller
public class EmpController {
	
	@Autowired
	EmpService empService;
	
	// 최초계정 등록.
	@ResponseBody
	@PostMapping("sol/customer/firstEmp")
	private Map<String, Object> insertFirstEmp(@RequestBody EmpVO empVO) {
		return empService.insertFirstEmp(empVO);
	}
	
	// 고객사 소속 계정 전체조회
	@PostMapping("sol/customer/emps")
	public String custEmpList(SearchVO search, String custNo, Model model) {
		if(search.getPage() == 0) {
			search.setPage(1);
		}
		
		List<EmpVO> list = empService.selectEmpAll(custNo, search);
		int count = empService.countEmpAll(custNo, search);	
		PageDTO pageDTO = new PageDTO(search.getPage(), count);
		model.addAttribute("list", list);
		model.addAttribute("pageDTO", pageDTO);
		
		return "solution/cust/modal :: #EmpListArea";
	}
	
	
}
