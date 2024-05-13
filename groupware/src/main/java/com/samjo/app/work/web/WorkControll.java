package com.samjo.app.work.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.samjo.app.work.service.WorkService;
import com.samjo.app.work.service.WorkVO;

@Controller
public class WorkControll {

	@Autowired
	WorkService workService;
	
	@GetMapping("worklist")
	public String workList(Model model) {
		List<WorkVO> list = workService.workList();
		model.addAttribute("works", list);
		return "work/worklist";
	}
	
	
	
}
