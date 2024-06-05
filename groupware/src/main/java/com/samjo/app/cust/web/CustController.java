package com.samjo.app.cust.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.samjo.app.cust.service.CustService;
import com.samjo.app.cust.service.CustVO;

@Controller
public class CustController {
	
	@Autowired
	CustService custService;
	
	// 등록 - form.
	@GetMapping("sol/customer/insert")
	public String custSignUpForm(Model model) {
		model.addAttribute("cust", new CustVO());
		return "solution/cust/insert";
	}
	
	@PostMapping("sol/customer/insert")
	public String custSignUpProcess(CustVO custVO) {
		
		String result = custService.insertCust(custVO);
		if(result == null) {
			return "test/test";
		} 
		
		return "redirect:/sol/customer/info?custNo=" + result;
	}
	
	@GetMapping("sol/customer/info")
	public String custInfo(Model model, @RequestParam String custNo) {
		
		CustVO cust = custService.selectCustInfo(custNo);
		model.addAttribute("cust", cust);
		
		return "solution/cust/info";
	}

}
