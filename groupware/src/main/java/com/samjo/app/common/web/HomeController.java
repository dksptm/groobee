package com.samjo.app.common.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/")
	public String BasicPage() {
		return "common/basic";
	}
	
	@GetMapping("/introduce")
	public String IntroducePage() {
		return "common/introduce";
	}
	
	@GetMapping("/home")
	public String HomePage() {
		return "test/test";
	}
	
}
