package com.samjo.app.solmodule.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ModuleController {

	@GetMapping("/soltemplate")
	public String templatePage() {
		return "solution/module/template";
	}
	
	@GetMapping("/insertSolTemp")
	public String InsertTemplate() {
		return "solution/module/insertTemp";
	}
}
