package com.samjo.app.project.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

	// 업무 전체조회
	@GetMapping("cust/taskAllList")
	public String taskListPage(SearchVO searchVO, Model model) {
		if (searchVO.getPage() <= 0) {
			searchVO.setPage(1);
		}
		if (searchVO.getTaskSort() == null || searchVO.getTaskSort().trim().isEmpty()) {
			searchVO.setTaskSort("task_no");
		}
		// System.out.println(searchVO);
		List<ProjectVO> list = taskService.taskAllList(searchVO);
		model.addAttribute("list", list);
		TaskDTO taskDTO = new TaskDTO(searchVO.getPage(), taskService.count(searchVO));
		model.addAttribute("TaskDTO", taskDTO);
		return "project/task/tsList";
	}

	// 업무 조회페이지 검색/페이징 처리
	@PostMapping("cust/viewTsList")
	public String viewTsListPage(SearchVO searchVO, Model model) {
		// System.out.println("searchVO: "+searchVO);
		// System.out.println("startDay : "+ searchVO.getTaskStart());
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

	// 업무 등록 - 페이지
	@GetMapping("cust/taskInsert")
	public String taskInsertForm(SearchVO searchVO, Model model) {
		List<DeptVO> list = deptService.deptAllList();
		model.addAttribute("dept", list);

		List<ProjectVO> pjlist = projectService.prjtList(searchVO);
		model.addAttribute("pjlist", pjlist);

		EmpVO empVO = SecuUtil.getLoginEmp();
		List<EmpVO> elist = deptService.myCustEmps(empVO.getCustNo());
		model.addAttribute("emp", elist);

		model.addAttribute("task", new ProjectVO());
		return "project/task/insert";
	}

	// 업무 등록 - 처리
	@ResponseBody
	@PostMapping("cust/taskInsert")
	public String taskInsertProcess(@RequestBody ProjectVO projectVO, Model model) {

		EmpVO empVO = SecuUtil.getLoginEmp();

		projectVO.setTaskType("5A1a");
		projectVO.setPrjtMat("5B1b");
		projectVO.setCustNo(empVO.getCustNo());

		model.addAttribute("task", new ProjectVO());

		int taskNo = taskService.taskInsert(projectVO);
		// System.out.println("taskNo---->" + taskNo);

		if (taskNo > -1) {
			return "" + taskNo;
		} else {
			return "test/test";
		}
	}

	// 업무 단건
	@GetMapping("cust/tsInfo/{taskNo}")
	public String taskInfo(@PathVariable int taskNo, Model model) {
		ProjectVO projectVO = taskService.taskInfo(taskNo);
		model.addAttribute("task", projectVO);
		return "project/task/tsInfo";
	}

	// 업무참여자 수정
	@ResponseBody
	@PutMapping("cust/taskOk/{taskNo}")
	public Map<String, Object> taskOk(@PathVariable Integer taskNo, @RequestBody ProjectVO projectVO) {
		// System.out.println("프로젝트VO=====>" + projectVO);
		return taskService.taskOk(projectVO);
	}

	// 업무 전체 수정 - 페이지
	@GetMapping("cust/tsModify")
	public String taskModifyForm(@RequestParam int taskNo, Model model) {
		ProjectVO projectVO = new ProjectVO();
		projectVO.setTaskNo(taskNo);

		ProjectVO ts = taskService.taskInfo(taskNo);
		model.addAttribute("task", ts);

		return "project/task/tsModify";

	}

	// 업무 전체 수정 - 처리
	@ResponseBody
	@PostMapping("cust/tsModify")
	public String taskModifyProcess(@RequestBody ProjectVO projectVO) {

		EmpVO empVO = SecuUtil.getLoginEmp();

		String custNo = empVO.getCustNo();
		projectVO.setCustNo(custNo);

		taskService.taskModify(projectVO);
		return "redirect:/cust/tsModify?taskNo=" + projectVO.getTaskNo();
	}

	// 협력업체 전체 조회
	@GetMapping("cust/coopAllList")
	public String coopAllList(SearchVO searchVO, Model model) {
		if (searchVO.getPage() <= 0) {
			searchVO.setPage(1);
		}
		if (searchVO.getCoSort() == null || searchVO.getCoSort().trim().isEmpty()) {
			searchVO.setCoSort("coop_co_no");
		}

		List<ProjectVO> list = taskService.CoopCoAllList(searchVO);
		model.addAttribute("clist", list);

		EmpVO empVO = SecuUtil.getLoginEmp();
		List<ProjectVO> tlist = taskService.taskList(empVO);
		model.addAttribute("tlist", tlist);

		TaskDTO taskDTO = new TaskDTO(searchVO.getPage(), taskService.coCount(searchVO));
		model.addAttribute("TaskDTO", taskDTO);
		return "project/coopCo/clist";
	}

	// 협력업체 조회페이지 검색/페이징 처리
	@PostMapping("cust/viewCoList")
	public String viewCoListPage(SearchVO searchVO, Model model) {

		if (searchVO.getPage() <= 0) {
			searchVO.setPage(1);
		}
		if (searchVO.getCoSort() == null || searchVO.getCoSort().trim().isEmpty()) {
			searchVO.setCoSort("coop_co_no");
		}
		List<ProjectVO> list = taskService.CoopCoAllList(searchVO);
		model.addAttribute("clist", list);

		TaskDTO taskDTO = new TaskDTO(searchVO.getPage(), taskService.coCount(searchVO));
		model.addAttribute("TaskDTO", taskDTO);
		return "project/coopCo/clist :: #coTable";
	}

	// 협력업체 단건조회.
	@GetMapping("cust/cInfo")
	public String coopInfo(@RequestParam int coopCoNo, Model model) {

		ProjectVO co = taskService.coopInfo(coopCoNo);
		model.addAttribute("coop", co);

		return "project/coopCo/cInfo";

	}

	// 협력업체 등록 (모달 데이터 등록 처리)
	@GetMapping("cust/coopInsert")
	public String coopInsertForm(@PathVariable("taskNo") int taskNo, Model model) {

		ProjectVO projectVO = new ProjectVO();
		projectVO.setTaskNo(taskNo);

		model.addAttribute("coop", projectVO);
		return "project/coopCo/clist";
	}

	// 협력업체 등록 처리
	@ResponseBody
	@PostMapping("cust/coopInsert")
	public String coopInsertProcess(@RequestBody ProjectVO projectVO, Model model) {
		// projectVO.setTaskNo(taskNo);

		int cNo = taskService.coopInsert(projectVO);
		String uri = null;

		if (cNo > -1) {
			uri = "true";
		} else {
			uri = "false";
		}
		return uri;
	}

	// 협력업체 수정 - 페이지
	@GetMapping("cust/coopUpdate")
	public String coopUpdateForm(@RequestParam Integer coopCoNo, Model model) {
		ProjectVO projectVO = new ProjectVO();

		projectVO.setCoopCoNo(coopCoNo);

		ProjectVO co = taskService.coopInfo(coopCoNo);
		model.addAttribute("coop", co);

		return "project/coopCo/cUpdate";
	}

	// 협력업체 수정 - 처리
	@ResponseBody
	@PostMapping("cust/coopUpdate")
	public String coopUpdateProcess(@RequestBody ProjectVO projectVO) {

		EmpVO empVO = SecuUtil.getLoginEmp();

		String custNo = empVO.getCustNo();
		projectVO.setCustNo(custNo);

		taskService.coopUpdate(projectVO);

		return "redirect:/cust/coopAllList";
	}
	

	  // 협력업체 삭제

	  @GetMapping("cust/coopDelete") 
	  public String coopDelete(ProjectVO projectVO)
	  { taskService.coopDelete(projectVO); return "redirect:cust/coopAllList"; }

	
	//효주 -----
	@GetMapping("cust/custTasks")
	public String getMyTasks(@RequestParam String custNo, Model model) {
		EmpVO empVO = SecuUtil.getLoginEmp();
		List<ProjectVO> list = projectService.myCustTasks(empVO);
		model.addAttribute("tasks", list);
		return "approval/modal/modal_tasks";
	}
	//---- 효주.

}


