package com.samjo.app.project.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.samjo.app.common.util.SecuUtil;
import com.samjo.app.emp.service.EmpVO;
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
				uri = "" ;
			} else {
				uri = "";
			}
			return uri;
		}
		
		// 상시 단건
		@GetMapping("reguInfo")
		public String reguInfo(ProjectVO projectVO, Model model) {
			ProjectVO findVO = projectService.coopInfo(projectVO);
			model.addAttribute("coopCo", findVO);
			return "project/regu/list";
		}
		
		// 상시 수정
		@PostMapping("reguUpdate")
		public String reguUpdateForm(@RequestParam String reguId, Model model) {
			ProjectVO projectVO = new ProjectVO();
			projectVO.setReguId(reguId);
			
			ProjectVO findVO = projectService.reguInfo(projectVO);
			model.addAttribute("reguInfo", findVO);
			return "project/regu/list";
		}
		/*@ResponseBody
		public Map<String, Object> reguUpdateProcessAjax(@RequestBody ProjectVO projectVO) {
			return projectService.reguUpdate(projectVO);
		}*/
		
		// 상시 삭제 
		@GetMapping("reguDelete")
		public String reguDelete(ProjectVO projectVO) {
			projectService.reguDelete(projectVO);
		return "redirect:reguAllList";
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
		// 협력업체 등록 (모달)
		@GetMapping("coopInsert")
		public String coopInsertForm(Model model) {
			model.addAttribute("coopCo", new ProjectVO());
			return "project/coopCo/list";
		}
		// 협력업체 등록 처리
		@ResponseBody
		@PostMapping("coopInsert")
		public String coopInsertProcess(@RequestBody ProjectVO projectVO) {
			

			//ProjectVO.setTaskNo(int);
			
			int cNo =  projectService.coopInsert(projectVO);
			String uri = null;
			
			if(cNo > -1) {
				uri = "true";
			} else {
				uri = "false";			
			}
			return uri;
		}
		
		// 협력업체 수정
		@PostMapping("coopUpdate")
		public String coopUpdateForm(@RequestParam Integer coopCoNo, Model model) {
			ProjectVO projectVO = new ProjectVO();
			projectVO.setCoopCoNo(coopCoNo);
			
			ProjectVO findVO = projectService.coopInfo(projectVO);
			model.addAttribute("coopInfo", findVO);
			return "project/coopCo/list";
		}
		
		@ResponseBody
		public Map<String, Object> coopUpdateProcessAjax(@RequestBody ProjectVO projectVO) {
			return projectService.coopUpdate(projectVO);
		}
		
		// 협력업체 삭제
		@GetMapping("coopDelete")
		public String coopDelete(ProjectVO projectVO) {
			projectService.coopDelete(projectVO);
		return "redirect:coopAllList";
		}
		
		/*public String coopDelete(ProjectVO projectVO, Model model) {
		    Map<String, Object> deletionResult = projectService.coopDelete(projectVO);
		    if (deletionResult.containsKey("coopCoNo")) {
		        Long deletedCoopCoNo = (Long) deletionResult.get("coopCoNo");
		        // 삭제 성공한 경우에 대한 처리 (예: 메시지 표시)
		        model.addAttribute("deletedCoopCoNo", deletedCoopCoNo);
		        model.addAttribute("message", "협력업체 삭제에 성공했습니다.");
		    } else {
		        // 삭제 실패한 경우에 대한 처리 (예: 메시지 표시)
		        model.addAttribute("message", "협력업체 삭제에 실패했습니다.");
		    }
		    return "redirect:coopAllList";
		}*/
		
		
		
	
		//효주 -----
		@GetMapping("getMyTasks")
		public String getMyTasks(@RequestParam String custNo, Model model) {
			List<ProjectVO> list = projectService.myCustTasks(custNo);
			model.addAttribute("tasks", list);
			return "approval/modal/modal_tasks";
		}
		//---- 효주.
		
}
