package com.samjo.app.manager.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ManagerController {
	
	@GetMapping("/manager")
	public String mainPage() {
		return "manager/test";
	}

}
