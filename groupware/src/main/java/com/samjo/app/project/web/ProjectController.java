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

import com.samjo.app.common.service.SearchVO;
import com.samjo.app.common.util.SecuUtil;
import com.samjo.app.ct.service.CtDTO;
import com.samjo.app.ct.service.CtVO;
import com.samjo.app.emp.service.DeptService;
import com.samjo.app.emp.service.DeptVO;
import com.samjo.app.emp.service.EmpVO;
import com.samjo.app.project.service.ProjectService;
import com.samjo.app.project.service.ProjectVO;
import com.samjo.app.project.service.TaskDTO;

@Controller
public class ProjectController {

	ProjectService projectService;
	DeptService deptService;

	@Autowired
	public ProjectController(ProjectService projectService, DeptService deptService) {
		this.projectService = projectService;
		this.deptService = deptService;
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
		System.out.println(findVO);
		return "project/prjt/info";
	}

	// 프로젝트 등록
	@GetMapping("prjtInsert")
	public String prjtInsertForm(Model model) {

		EmpVO empVO = SecuUtil.getLoginEmp();
		if (empVO != null) {
			List<EmpVO> list = deptService.respMngrList(empVO.getCustNo());
			model.addAttribute("mngr", list);
			model.addAttribute("projects", new ProjectVO());
			return "project/prjt/insert";
		} else {
			return "test/test";
		}

	}
	
	@ResponseBody
	@PostMapping("prjtInsert")
	public String prjtInsertProcess(ProjectVO projectVO) {
		EmpVO empVO = SecuUtil.getLoginEmp();
		String custNo = empVO.getCustNo();
		projectVO.setCustNo(custNo);
		projectVO.setPrjtStat("5E1e");
		int pId = projectService.prjtInsert(projectVO);
		String uri = null;

		if (pId > -1) {
			uri = "true";
		} else {
			uri = "fail";
		}
		return uri;
	}

	// 프로젝트 수정 - 페이지
	@GetMapping("prjtUpdate")
	public String prjtUpdateForm(ProjectVO projectVO, Model model) {

		ProjectVO findVO = projectService.prjtInfo(projectVO);
		model.addAttribute("prjtInfo", findVO);
		return "project/prjt/update";
	}

	// 수정 처리
	@PostMapping("prjtUpdate")
	@ResponseBody
	public Map<String, Object> prjtUpdateProcess(ProjectVO projectVO) {
		return projectService.prjtUpdate(projectVO);
	}

	// 프로젝트 삭제
	@GetMapping("prjtDelete")
	public String prjtDelete(ProjectVO projectVO) {
		projectService.prjtDelete(projectVO);
		return "redirect:prjtAllList";
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
		List<ProjectVO> list = projectService.taskAllList(searchVO);
		TaskDTO taskDTO = new TaskDTO(searchVO.getPage(), projectService.count(searchVO));
		model.addAttribute("TaskDTO", taskDTO);
		model.addAttribute("task", list);
		return "project/task/tsList";
	}
	
	//프로젝트 업무 조회페이지 검색/페이징 처리
	@PostMapping("viewTsList")
	public String viewTsListPage(SearchVO searchVO, Model model) {
		System.out.println("searchVO: "+searchVO);
		System.out.println("startDay : "+ searchVO.getTaskStart());
		if (searchVO.getPage() <= 0) {
			searchVO.setPage(1);
		}
		if (searchVO.getTaskSort() == null || searchVO.getTaskSort().trim().isEmpty()) {
			searchVO.setTaskSort("task_no");
		}
		List<ProjectVO> list = projectService.taskAllList(searchVO);
		model.addAttribute("list", list);
		TaskDTO taskDTO = new TaskDTO(searchVO.getPage(), projectService.count(searchVO));
		model.addAttribute("TaskDTO", taskDTO);
		return "project/task/tsList :: #taskTable";
	}
	 
	// 프로젝트(하위) 업무 등록
	@GetMapping("taskInsert")
	public String taskInsertForm(Model model) {
		List<DeptVO> list = deptService.deptAllList();
		model.addAttribute("dept", list);
		
		List<ProjectVO> plist = projectService.PrjtAllList();
		model.addAttribute("plist", plist);
		
		EmpVO empVO = SecuUtil.getLoginEmp();
		List<EmpVO> elist = deptService.myCustEmps(empVO.getCustNo());
		model.addAttribute("emp", elist);
		
		model.addAttribute("task", new ProjectVO());
		return "project/task/insert";
	}
	
	@PostMapping("task/insert")
	public String taskInsertProcess(@RequestBody ProjectVO projectVO) {
		
		EmpVO empVO = SecuUtil.getLoginEmp();
		
		projectVO.setTaskType("5A1a");
		projectVO.setPrjtMat("5B1b");
		projectVO.setCustNo(empVO.getCustNo());
		
		int taskNo = projectService.taskInsert(projectVO);
		
		String uri = null;

		if (taskNo > -1) {
			uri = "true";
		} else {
			uri = "false";
		}
		return uri;
	}
	
	
	
	// 프로젝트(하위) 업무 단건
	@GetMapping("taskInfo")
	public String taskInfo(ProjectVO projectVO, Model model) {
		ProjectVO findVO = projectService.taskInfo(projectVO);
		model.addAttribute("task", findVO);
		return "project/task/Info";
	}

	// 프로젝트(하위) 업무 수정
	@PostMapping("taskUpdate")
	public String taskUpdateForm(@RequestParam Integer taskNo, Model model) {
		ProjectVO projectVO = new ProjectVO();
		projectVO.setTaskNo(taskNo);

		ProjectVO findVO = projectService.taskInfo(projectVO);
		model.addAttribute("taskInfo", findVO);
		return "project/task/list";
	}

	@ResponseBody
	public Map<String, Object> taskUpdateProcessAjax(@RequestBody ProjectVO projectVO) {
		return projectService.taskUpdate(projectVO);
	}

	// 프로젝트(하위) 업무 삭제
	@GetMapping("taskDelete")
	public String empDelete(ProjectVO projectVO) {
		projectService.taskDelete(projectVO);
		return "redirect:taskAllList";
	}

}
