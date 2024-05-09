package com.samjo.app.approval.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.samjo.app.approval.service.DocService;
import com.samjo.app.approval.service.DocVO;

@Controller
public class DocController {
	
	DocService docService;
	
	@Autowired
	public DocController(DocService docService) {
		this.docService = docService;
	}
	
	@GetMapping("/hjTest")
	public String test() {
		return "approval/doc/test";
	}
	
	@GetMapping("/docInfo")
	public String docInfo() {
		return "approval/doc/info";
	}
	
	@GetMapping("/docInsert")
	public String dobInsertForm(Model model) {
		model.addAttribute("doc", new DocVO());
		return "approval/doc/insert";
	}
	
	@PostMapping("/docInsert")
	public String docInsertProcess(DocVO docVO) {
		docService.docInfoInsert(docVO);
		return "test/test";
	}
	
}
