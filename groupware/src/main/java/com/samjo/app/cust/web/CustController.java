package com.samjo.app.cust.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.samjo.app.common.service.PageDTO;
import com.samjo.app.common.service.SearchVO;
import com.samjo.app.cust.service.CustService;
import com.samjo.app.cust.service.CustVO;
import com.samjo.app.emp.service.EmpService;
import com.samjo.app.emp.service.EmpVO;

@Controller
public class CustController {
	
	@Autowired
	CustService custService;
	
	@Autowired
	EmpService empService;
	
	// 등록 - form.
	@GetMapping("sol/customer/insert")
	public String custSignUpForm(Model model) {
		model.addAttribute("cust", new CustVO());
		return "solution/cust/insert";
	}
	
	// 등록 - Process.
	@PostMapping("sol/customer/insert")
	public String custSignUpProcess(CustVO custVO) {
		
		String result = custService.insertCust(custVO);
		if(result == null) {
			return "test/test";
		} 
		
		return "redirect:/sol/customer/info?custNo=" + result;
	}
	
	// 단건조회.
	@GetMapping("sol/customer/info")
	public String custInfo(Model model, @RequestParam String custNo, SearchVO search) {
		if(search.getPage() == 0) {
			search.setPage(1);
		}
		
		CustVO cust = custService.selectCustInfo(custNo);
		model.addAttribute("cust", cust);
		
		List<EmpVO> list = empService.selectEmpAll(custNo, search);
		int count = empService.countEmpAll(custNo, search);	
		PageDTO pageDTO = new PageDTO(search.getPage(), count);
		model.addAttribute("list", list);
		model.addAttribute("pageDTO", pageDTO);
		
		return "solution/cust/info";
	}
	
	// 전체조회
	@GetMapping("sol/customer/list")
	public String custList(Model model, SearchVO search) {
		if(search.getPage() == 0) {
			search.setPage(1);
		}
		if(search.getSortCondition() == null || search.getSortCondition().equals("")) {
			search.setSortCondition("c.cust_no");
		}
		
		List<CustVO> list = custService.selectCusts(search);
		int count = custService.countCusts(search);
		PageDTO pageDTO = new PageDTO(search.getPage(), count);
		
		model.addAttribute("list", list);
		model.addAttribute("search", search);
		model.addAttribute("pageDTO", pageDTO);
		
		return "solution/cust/list";
	}
	
	// 전체조회 - 검색
	@PostMapping("sol/customer/list/sch")
	public String custListSearch(Model model, SearchVO search) {
		if(search.getPage() == 0) {
			search.setPage(1);
		}
		if(search.getSortCondition() == null || search.getSortCondition().equals("")) {
			search.setSortCondition("c.cust_no");
		}
		
		List<CustVO> list = custService.selectCusts(search);
		int count = custService.countCusts(search);
		PageDTO pageDTO = new PageDTO(search.getPage(), count);
		
		model.addAttribute("list", list);
		model.addAttribute("search", search);
		model.addAttribute("pageDTO", pageDTO);
		
		return "solution/cust/list :: #CustListArea";
	}

}
