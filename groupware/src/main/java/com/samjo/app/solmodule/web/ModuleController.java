package com.samjo.app.solmodule.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ModuleController {

	//템플릿 전체조회
	@GetMapping("/soltemplate")
	public String templatePage() {
		return "solution/module/template";
	}
	
	//템플릿 등록
	@GetMapping("/insertSolTemp")
	public String InsertTemplate() {
		return "solution/module/insertTemp";
	}
}
