package com.samjo.app.project.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.samjo.app.project.service.ProjectService;
import com.samjo.app.project.service.ProjectVO;
@Controller
public class TaskController {
	
	ProjectService projectService;
	
	@Autowired
	public TaskController(ProjectService projectService) {
		this.projectService = projectService;
	}
	
	// 상시(주기적)업무 등록
		@GetMapping("reguInsert")
		public String reguInsertForm(Model model) {
			model.addAttribute("projects", new ProjectVO());
			return "project/regu/insert";
		}
		
		@PostMapping("reguInsert")
		public String reguInsertProcess(ProjectVO projectVO) {
			int rId = projectService.prjtInsert(projectVO);
			String uri = null;
			
			if(rId > -1) {
				uri = "redirect:prjtInfo?prjtId=" + rId;
			} else {
				uri = "reguAllList";
			}
			return uri;
		}
		
		// 상시(주기적)업무 전체 조회
		@GetMapping("reguAllList")
		public String reguAllList(Model model) {
			List<ProjectVO> list = projectService.reguAllList();
			model.addAttribute("projects", list);
			return "project/regu/list";
		}
}
