package com.samjo.app.ct.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustController {
	
	@GetMapping("sol/cust/insert")
	public String custSignUpForm() {
		return "solution/cust/insert";
	}

}
