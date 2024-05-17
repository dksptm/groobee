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
		return "project/prjt/list";
	}
	
	// 프로젝트 단건조회
	@GetMapping("prjtInfo")
	public String prjtInfo(ProjectVO projectVO, Model model) {
		ProjectVO findVO = projectService.prjtInfo(projectVO);
		model.addAttribute("projects", findVO);
		return "project/prjt/list";
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
			uri = "prjtAllList";
		}
		return uri;
	}
	
	
	
	// 프로젝트(하위) 업무 전체조회
	@GetMapping("taskAllList")
	public String taskAllList(Model model) {
				List<ProjectVO> list = projectService.taskAllList();
				model.addAttribute("task", list);
				return "project/task/list";
			}
	// 프로젝트(하위) 업무 등록
	@GetMapping("taskInsert")
	public String taskInsertForm(Model model) {
				model.addAttribute("task", new ProjectVO());
				return "project/task/insert";
			}
			
	@PostMapping("taskInsert")
	public String taskInsertProcess(ProjectVO projectVO) {
				int tNo = projectService.taskInsert(projectVO);
				String uri = null;
				
				if(tNo > -1) {
					uri = "redirect:taskInfo?taskNo=" + tNo;
				} else {
					uri = "taskAllList";
				}
				return uri;
			}
			
	// 프로젝트프로젝트(하위) 업무 단건
	@GetMapping("taskInfo")
	public String taskInfo(ProjectVO projectVO, Model model) {
				ProjectVO findVO = projectService.taskInfo(projectVO);
				model.addAttribute("task", findVO);
				return "project/task/info";
			}
			
			

	// 프로젝트 업무조회
	// 프로젝트 업무 수정
	//

	
	

}
