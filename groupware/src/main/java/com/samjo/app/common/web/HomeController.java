package com.samjo.app.common.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("loginType")
public class HomeController {

	//로그인 - 초기화면
	@GetMapping("/")
	public String BasicPage() {
		return "common/basic";
	}
	
	//로그인 - 솔루션소개
	@GetMapping("/introduce")
	public String IntroducePage() {
		return "common/introduce";
	}
	
	//고객사 메인페이지
	@GetMapping("/home")
	public String HomePage() {
		return "test/test";
	}
	
}
