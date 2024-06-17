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
import com.samjo.app.emp.service.DeptService;
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
	@GetMapping("cust/prjtAllList")
	public String prjtListPage(SearchVO searchVO, Model model) {
		if (searchVO.getPage() <= 0) {
			searchVO.setPage(1);
		}
		if (searchVO.getPrjtSort() == null || searchVO.getPrjtSort().trim().isEmpty()) {
			searchVO.setPrjtSort("prjt_id DESC");
		}
		EmpVO empVO = SecuUtil.getLoginEmp();
		List<ProjectVO> list = projectService.PrjtAllList(searchVO, empVO.getCustNo());
		model.addAttribute("pjlist", list);
		TaskDTO taskDTO = new TaskDTO(searchVO.getPage(), projectService.count(searchVO, empVO.getCustNo()));
		model.addAttribute("TaskDTO", taskDTO);
		return "project/prjt/pjList";
	}
	
	//프로젝트 조회페이지 검색/페이징 처리
	@PostMapping("cust/viewPjList")
	public String viewPjListPage(SearchVO searchVO, Model model) {

		if (searchVO.getPage() <= 0) {
			searchVO.setPage(1);
		}
		if (searchVO.getPrjtSort() == null || searchVO.getPrjtSort().trim().isEmpty()) {
				searchVO.setPrjtSort("prjt_id DESC");
		}
			EmpVO empVO = SecuUtil.getLoginEmp();
			List<ProjectVO> list = projectService.PrjtAllList(searchVO, empVO.getCustNo());
			model.addAttribute("pjlist", list);
			TaskDTO taskDTO = new TaskDTO(searchVO.getPage(), projectService.count(searchVO, empVO.getCustNo()));
			model.addAttribute("TaskDTO", taskDTO);
			return "project/prjt/pjList :: #prjtTable";
	}
	
	// 프로젝트 단건조회 
	@GetMapping("/cust/pj/info")
	public String prjtInfo(@RequestParam String prjtId, Model model) {
		
		EmpVO empVO = SecuUtil.getLoginEmp();
		
		if(empVO != null) {
			
			ProjectVO pj = projectService.prjtInfo(prjtId, empVO.getCustNo());
			model.addAttribute("prjt", pj);
			
			List<ProjectVO> list = projectService.taskList(prjtId, empVO.getCustNo());
			model.addAttribute("list", list);
			
		return "project/prjt/pjInfo";
	}
		return "test/test";
	}

	// 프로젝트 등록
	@GetMapping("cust/prjtInsert")
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
	@PostMapping("cust/prjtInsert")
	public String prjtInsertProcess(ProjectVO projectVO) {
		
		EmpVO empVO = SecuUtil.getLoginEmp();
		
		String custNo = empVO.getCustNo();
		projectVO.setCustNo(custNo);
		projectVO.setPrjtStat("5E1e");
		
		int pId = projectService.prjtInsert(projectVO);
		//projectService.prjtInsert(projectVO);
		System.out.println("pId---->" + pId);	
		System.out.println(" projectVO.getPrjtId()----"+ projectVO.getPrjtId());
		return "/cust/pj/info?prjtId=" + projectVO.getPrjtId();
		/*
		 * if (pId > -1) { return "redirect:/cust/pj/info?prjtId=" +
		 * projectVO.getPrjtId();
		 * 
		 * } else { return "test/test"; }
		 */
	}

	// 프로젝트 수정 - 페이지
	@GetMapping("cust/prjtModify")
	public String prjtModifyForm(@RequestParam String prjtId, Model model) {
		ProjectVO projectVO = new ProjectVO();
		projectVO.setPrjtId(prjtId);
		
		
		EmpVO empVO = SecuUtil.getLoginEmp();
		if (empVO != null) {
			
				ProjectVO pj = projectService.prjtInfo(prjtId, empVO.getCustNo());
				model.addAttribute("prjt", pj);
				
				List<EmpVO> list = deptService.respMngrList(empVO.getCustNo());
				model.addAttribute("mngr", list);
				model.addAttribute("projects", new ProjectVO());
			return"project/prjt/pjModify";
		} else {
			return "test/test";
		}
	}
	
	// 프로젝트 수정 - 처리
	@PostMapping("cust/prjtModify")
	@ResponseBody
	public String prjtModifyProcess(@RequestBody ProjectVO projectVO) {
		
		EmpVO empVO = SecuUtil.getLoginEmp();
		
		String custNo = empVO.getCustNo();
		projectVO.setCustNo(custNo);
		
		projectService.prjtModify(projectVO);
		return "redirect:/cust/pj/info?prjtId=" + projectVO.getPrjtId();
	
	}

}// end 
		
