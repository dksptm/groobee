package com.samjo.app.project.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.samjo.app.project.service.ProjectService;
import com.samjo.app.project.service.ProjectVO;
@Controller
public class TaskController {
	
	ProjectService projectService;
	
	@Autowired
	public TaskController(ProjectService projectService) {
		this.projectService = projectService;
	}
	
	// 상시(주기적)업무 전체 조회
		@GetMapping("reguAllList")
		public String reguAllList(Model model) {
			List<ProjectVO> list = projectService.reguAllList();
			model.addAttribute("projects", list);
			return "project/regu/list";
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
	
		
		// 협력업체 전체 조회
		@GetMapping("coopAllList")
		public String coopAllList(Model model) {
			List<ProjectVO> list = projectService.CoopCoAllList();
			model.addAttribute("coopCo", list);
			return "project/coopCo/list";
		}
		// 협력업체 단건조회.
		@GetMapping("coopInfo")
		public String coopInfo(ProjectVO projectVO, Model model) {
			ProjectVO findVO = projectService.coopInfo(projectVO);
			model.addAttribute("coopCo", findVO);
			return "project/coopCo/list";
		}
		// 협력업체 등록
		@GetMapping("coopInsert")
		public String coopInsertForm(Model model) {
			model.addAttribute("coopCo", new ProjectVO());
			return "project/coopCo/list";
		}
		
		@PostMapping("coopInsert")
		public String coopInsertProcess(ProjectVO projectVO) {
			int cNo =  projectService.coopInsert(projectVO);
			String uri = null;
			
			if(cNo > -1) {
				uri = "redirect:coopInfo?coopCoNo=" + cNo;
			} else {
				uri = "coopAllList";			
			}
			return uri;
		}
		
		// 협력업체 수정
		

		// 협력업체 삭제
		@GetMapping("coopDelete")
		public String coopDelete(ProjectVO projectVO) {
			projectService.coopDelete(projectVO);
			return "redirect:coopAllList";
		}
		
		
		
		
		
		
		
		
		
		
	
		//효주 -----
		@GetMapping("getMyTasks")
		public String getMyTasks(@RequestParam String custNo, Model model) {
			List<ProjectVO> list = projectService.myCustTasks(custNo);
			model.addAttribute("tasks", list);
			return "approval/modal/modal_tasks";
		}
		//---- 효주.
		
}
