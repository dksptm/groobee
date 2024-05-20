package com.samjo.app.solmodule.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.samjo.app.approval.service.TempVO;
import com.samjo.app.solmodule.service.ModuleService;

@RestController
public class ModuleRestController {
	
	@Autowired
	ModuleService moduleservice;
	
	//템플릿 등록 처리
	@PostMapping("insertSolTemp")
	public String tempInsert(@RequestBody TempVO tempVO) {
		return moduleservice.tempInsert(tempVO);  
	}
}
