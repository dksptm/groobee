package com.samjo.app.common.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

	@Autowired
	private HttpSession session;

	// 로그인 - 초기화면
	@GetMapping("/")
	public String BasicPage() {
		return "common/basic";
	}

	// 로그인 - 솔루션소개
	@GetMapping("/introduce")
	public String IntroducePage() {
		return "common/introduce";
	}

	// 로그인 페이지
	@GetMapping("/loginPage")
	public String loginPage(Model model,
            @RequestParam(value="error", required = false) String error,
            @RequestParam(value = "exception", required = false) String exception) {
			model.addAttribute("error", error);
	        model.addAttribute("exception", exception);
	        System.out.println("error : "+ error);
	        System.out.println("exception : "+ exception);
		return "common/login";
	}
	
	// 고객사 메인페이지
	@GetMapping("/home")
	public String HomePage(Model model) {
		return "test/test";
	}

	// 솔루션 메인페이지
	@GetMapping("/solHome")
	public String SolHomePage(Model model) {

		if (session != null) {
			session.invalidate();
		}
		return "test/test";
	}
	
	// 에러페이지
	@GetMapping("/error2")
	public String ErrorPage(Model model) {
		return "common/error";
	}
}
