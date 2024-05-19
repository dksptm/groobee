package com.samjo.app.work.web;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.samjo.app.work.service.WorkPageDTO;
import com.samjo.app.work.service.WorkSearchVO;
import com.samjo.app.work.service.WorkService;
import com.samjo.app.work.service.WorkVO;

@Controller
public class WorkControll {
	
	

	@Autowired
	WorkService workService;
	
	@GetMapping("worklist")
	public String workList(WorkSearchVO worksearchVO, Model model) {
		
		if(worksearchVO.getOneDate() == null ) {
			worksearchVO.setOneDate(new Date());
			
		}
		/*
		 * if(worksearchVO.getTwoDate() == null ) { worksearchVO.setTwoDate(new Date());
		 * }
		 */
			
			
		if(worksearchVO.getWkYn() == null) {
			worksearchVO.setWkYn("근무일");
		}
		if(worksearchVO.getWkSite() == null) {
			worksearchVO.setWkSite("내근");
		}
		if(worksearchVO.getWkStat() == null) {
			worksearchVO.setWkStat("정상근무");
		}
		if(worksearchVO.getPage() == 0) {
			worksearchVO.setPage(1);
		}
		List<WorkVO> list = workService.workList(worksearchVO);
		model.addAttribute("list", list);
		WorkPageDTO workpageDTO = new WorkPageDTO(worksearchVO.getPage(), workService.workcount());
		model.addAttribute("filter", workpageDTO);
		return "work/worklist";
	}
	
	
	 
	
	
	
}
