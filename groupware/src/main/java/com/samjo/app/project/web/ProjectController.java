package com.samjo.app.project.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.samjo.app.project.service.CoopCoVO;
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
		return "project/prjt/list";
	}
	
	// 프로젝트 단건조회
	@GetMapping("prjtInfo")
	public String prjtInfo(ProjectVO projectVO, Model model) {
		ProjectVO findVO = projectService.prjtInfo(projectVO);
		model.addAttribute("projects", findVO);
		return "project/prjt/info";
	}
	
	// 프로젝트 등록
	@GetMapping("prjtInsert")
	public String prjtInsertForm(Model model) {
		model.addAttribute("projects", new ProjectVO());
		return "project/prjt/insert";
	}
	
	@PostMapping("prjtInsert")
	public String prjtInsertProcess(ProjectVO projectVO) {
		int pId = projectService.prjtInsert(projectVO);
		String uri = null;
		
		if(pId > -1) {
			uri = "redirect:prjtInfo?prjtId=" + pId;
		} else {
			uri = "project/prjt/list";
		}
		return uri;
	}
	
	
	// 협력업체 전체 조회
	@GetMapping("coopAllList")
	public String coopAllList(Model model) {
		List<CoopCoVO> list = projectService.CoopCoAllList();
		model.addAttribute("coopCo", list);
		return "project/coopCo/list";
	}
	// 협력업체 단건조회.
	@GetMapping("coopInfo")
	public String coopInfo(CoopCoVO coopCoVO, Model model) {
		CoopCoVO findVO = projectService.coopInfo(coopCoVO);
		model.addAttribute("coopCo", findVO);
		return "project/coopCo/info";
	}
	
	 
	// 협력업체 등록
	@GetMapping("coopInsert")
	public String coopInsertForm(Model model) {
		model.addAttribute("coopCo", new CoopCoVO());
		return "project/coopCo/insert";
	}
	
	@PostMapping("coopInsert")
	public String coopInsertProcess(CoopCoVO coopCoVO) {
		int cNo =  projectService.coopInsert(coopCoVO);
		String uri = null;
		
		if(cNo > -1) {
			uri = "redirect:coopInfo?coopCoNo=" + cNo;
		} else {
			uri = "coopAllList";			
		}
		return uri;
	}
	









}
