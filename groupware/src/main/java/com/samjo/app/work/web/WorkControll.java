package com.samjo.app.work.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.samjo.app.work.service.WorkPageDTO;
import com.samjo.app.work.service.WorkService;
import com.samjo.app.work.service.WorkVO;

@Controller
public class WorkControll {

	@Autowired
	WorkService workService;
	
	@GetMapping("worklist")
	public String workList(WorkPageDTO workpagedto, Model model) {
		if(workpagedto.getPage() == 0) {
			workpagedto.setPage(1);
		}
		List<WorkVO> list = workService.workList();
		model.addAttribute("works", list);
		WorkPageDTO workpageDTO = new WorkPageDTO(workpagedto.getPage(), workService.workcount());
		model.addAttribute("workpageDTO", workpageDTO);
		return "work/worklist";
	}
	
	//@PostMapping("worklist")
	
	
	
}
