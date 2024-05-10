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
public class ProjectController {

	ProjectService projectService;
	
	@Autowired
	public ProjectController(ProjectService projectService) {
		this.projectService = projectService;
	}
	
	// 프로젝트 전체 조회
	@GetMapping("prjtAllList")
	public String prjtAllList(Model model) {
		List<ProjectVO> list = projectService.PrjtAllList();
		model.addAttribute("projects", list);
		return "project/list";
	}
	
	// 프로젝트 단건조회
	
	
	// 프로젝트 등록
	@GetMapping("prjtInsert")
	public String prjtInsertForm(Model model) {
		model.addAttribute("projects", new ProjectVO());
		return "project/insert";
	}
	
	@PostMapping("prjtInsert")
	public String prjtInsertProcess(ProjectVO projectVO) {
		int pId = projectService.prjtInsert(projectVO);
		String uri = null;
		
		if(pId > -1) {
			uri = "redirect:prjtInfo?prjtId=" + pId;
		} else {
			uri = "prjtList";
		}
		return uri;
	}
	
	
	
}
