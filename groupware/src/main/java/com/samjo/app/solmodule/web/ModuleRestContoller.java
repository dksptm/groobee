package com.samjo.app.solmodule.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.samjo.app.approval.service.TempVO;
import com.samjo.app.solmodule.service.ModuleService;

@RestController
public class ModuleRestContoller {
	@Autowired
	ModuleService modservice;
	
	//템플릿 수정
	@PutMapping("tempUpdate/{id}")
	public String tempUpdate(@PathVariable String id, @RequestBody TempVO tempVO){
		tempVO.setTempNo(id);
		System.out.println(id);
		modservice.tempUpdate(tempVO);
		return "redirect:/solTempInfo?tempNo="+id;
	}
}
