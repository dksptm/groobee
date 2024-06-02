package com.samjo.app.project.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.samjo.app.common.service.SearchVO;
import com.samjo.app.common.util.SecuUtil;
import com.samjo.app.emp.service.DeptService;
import com.samjo.app.emp.service.DeptVO;
import com.samjo.app.emp.service.EmpVO;
import com.samjo.app.project.service.ProjectService;
import com.samjo.app.project.service.ProjectVO;
import com.samjo.app.project.service.TaskDTO;
import com.samjo.app.project.service.TaskEmpsVO;
import com.samjo.app.project.service.TaskService;
@Controller
public class TaskController {
	
	ProjectService projectService;
	DeptService deptService;
	TaskService taskService;
	
	@Autowired
	public TaskController(ProjectService projectService, DeptService deptService, TaskService taskService) {
		this.projectService = projectService;
		this.deptService = deptService;
		this.taskService = taskService;
	}
	
	// 프로젝트(하위) 업무 전체조회
		@GetMapping("taskAllList")
		public String taskListPage(SearchVO searchVO, Model model) {
			if (searchVO.getPage() <= 0) {
				searchVO.setPage(1);
			}
			if (searchVO.getTaskSort() == null || searchVO.getTaskSort().trim().isEmpty()) {
				searchVO.setTaskSort("task_no");
			}
			//System.out.println(searchVO);
			List<ProjectVO> list = taskService.taskAllList(searchVO);
			model.addAttribute("list", list);
			TaskDTO taskDTO = new TaskDTO(searchVO.getPage(), taskService.count(searchVO));
			model.addAttribute("TaskDTO", taskDTO);
			return "project/task/tsList";
		}
		
		//프로젝트 업무 조회페이지 검색/페이징 처리
		@PostMapping("viewTsList")
		public String viewTsListPage(SearchVO searchVO, Model model) {
			//System.out.println("searchVO: "+searchVO);
			//System.out.println("startDay : "+ searchVO.getTaskStart());
			if (searchVO.getPage() <= 0) {
				searchVO.setPage(1);
			}
			if (searchVO.getTaskSort() == null || searchVO.getTaskSort().trim().isEmpty()) {
				searchVO.setTaskSort("task_no");
			}
			List<ProjectVO> list = taskService.taskAllList(searchVO);
			model.addAttribute("list", list);
			TaskDTO taskDTO = new TaskDTO(searchVO.getPage(), taskService.count(searchVO));
			model.addAttribute("TaskDTO", taskDTO);
			return "project/task/tsList :: #taskTable";
		}
		 
		// 프로젝트(하위) 업무 등록
		@GetMapping("taskInsert")
		public String taskInsertForm(SearchVO searchVO, Model model) {
			List<DeptVO> list = deptService.deptAllList();
			model.addAttribute("dept", list);
			
			List<ProjectVO> plist = projectService.PrjtAllList(searchVO);
			model.addAttribute("plist", plist);
			
			EmpVO empVO = SecuUtil.getLoginEmp();
			List<EmpVO> elist = deptService.myCustEmps(empVO.getCustNo());
			model.addAttribute("emp", elist);
			
			model.addAttribute("task", new ProjectVO());
			return "project/task/insert";
		}
		
		@PostMapping("task/insert")
		public String taskInsertProcess(@RequestBody ProjectVO projectVO,Model model) {
			
			EmpVO empVO = SecuUtil.getLoginEmp();
			
			projectVO.setTaskType("5A1a");
			projectVO.setPrjtMat("5B1b");
			projectVO.setCustNo(empVO.getCustNo());
			
			model.addAttribute("task", new ProjectVO());
			
			int taskNo = taskService.taskInsert(projectVO);
			String uri = null;

			if (taskNo > -1) {
				uri = "redirect:/taskAllList";
			} else {
				uri = "test/test";
			}
			return uri;
		}
		
		// 프로젝트(하위) 업무 단건
		@GetMapping("tsInfo/{taskNo}")
		public String taskInfo(@PathVariable int taskNo, Model model) {
			ProjectVO projectVO = taskService.taskInfo(taskNo);	
			model.addAttribute("task", projectVO);
			return "project/task/tsInfo";
		
		}
		
		// 업무참여자 수정
		@ResponseBody
		@PutMapping("taskOk/{taskNo}")
		public Map<String, Object> taskOk(@PathVariable Integer taskNo, @RequestBody ProjectVO projectVO) {
			System.out.println("프로젝트VO=====>" + projectVO);
			
			return taskService.taskOk(projectVO);
		}
		
		// 프로젝트(하위) 업무 수정
		@PostMapping("taskUpdate/{taskNo}")
		public String taskUpdate(@PathVariable int taskNo, Model model) {
			ProjectVO projectVO = new ProjectVO();
			projectVO.setTaskNo(taskNo);

			ProjectVO findVO = taskService.taskInfo(taskNo);
			model.addAttribute("taskInfo", findVO);
			return "project/task/list";
		}
		/*
		 * @ResponseBody public Map<String, Object> taskUpdateProcessAjax(@RequestBody
		 * ProjectVO projectVO) { return projectService.taskUpdate(projectVO); }
		 */

		// 프로젝트(하위) 업무 삭제
		@GetMapping("taskDelete")
		public String taskDelete(ProjectVO projectVO) {
			taskService.taskDelete(projectVO);
			return "redirect:taskAllList";
		}
	
		// 협력업체 전체 조회
		@GetMapping("coopAllList")
		public String coopAllList(Model model) {
			List<ProjectVO> list = taskService.CoopCoAllList();
			model.addAttribute("coopCo", list);
			return "project/coopCo/list";
		}
		// 협력업체 단건조회.
		@GetMapping("coopInfo")
		public String coopInfo(ProjectVO projectVO, Model model) {
			ProjectVO findVO = taskService.coopInfo(projectVO);
			model.addAttribute("coopCo", findVO);
			return "project/coopCo/list";
		}
		// 협력업체 등록 (모달)
		@GetMapping("coopInsert")
		   public String coopInsertForm(@PathVariable("taskNo") int taskNo, Model model) {
	        ProjectVO projectVO = new ProjectVO();
	        projectVO.setTaskNo(taskNo); // task_no를 ProjectVO에 설정
	        model.addAttribute("coopCo", projectVO);
	        return "project/coopCo/list";
	    }
		// 협력업체 등록 처리
		@ResponseBody
		@PostMapping("coopInsert")
		public String coopInsertProcess(@RequestBody ProjectVO projectVO) {
			
			//ProjectVO.setTaskNo(int);
			int cNo =  taskService.coopInsert(projectVO);
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
			
			ProjectVO findVO = taskService.coopInfo(projectVO);
			model.addAttribute("coopInfo", findVO);
			return "project/coopCo/list";
		}
		
		@ResponseBody
		public Map<String, Object> coopUpdateProcessAjax(@RequestBody ProjectVO projectVO) {
			return taskService.coopUpdate(projectVO);
		}
		
		// 협력업체 삭제
		@GetMapping("coopDelete")
		public String coopDelete(ProjectVO projectVO) {
			taskService.coopDelete(projectVO);
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
		@GetMapping("cust/custTasks")
		public String getMyTasks(@RequestParam String custNo, Model model) {
			List<ProjectVO> list = projectService.myCustTasks(custNo);
			model.addAttribute("tasks", list);
			return "approval/modal/modal_tasks";
		}
		//---- 효주.
		
}
