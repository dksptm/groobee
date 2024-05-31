package com.samjo.app.project.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	@GetMapping("prjtAllList")
	public String prjtListPage(SearchVO searchVO, Model model) {
		if (searchVO.getPage() <= 0) {
			searchVO.setPage(1);
		}
		if (searchVO.getPrjtSort() == null || searchVO.getPrjtSort().trim().isEmpty()) {
			searchVO.setPrjtSort("prjt_id");
		}
		
		List<ProjectVO> list = projectService.PrjtAllList(searchVO);
		model.addAttribute("pjlist", list);
		TaskDTO taskDTO = new TaskDTO(searchVO.getPage(), projectService.count(searchVO));
		model.addAttribute("TaskDTO", taskDTO);
		return "project/prjt/pjList";
	}
	
	//프로젝트 조회페이지 검색/페이징 처리
	@PostMapping("viewPjList")
	public String viewPjListPage(SearchVO searchVO, Model model) {

		if (searchVO.getPage() <= 0) {
			searchVO.setPage(1);
		}
		if (searchVO.getPrjtSort() == null || searchVO.getPrjtSort().trim().isEmpty()) {
				searchVO.setPrjtSort("prjt_id");
		}
			List<ProjectVO> list = projectService.PrjtAllList(searchVO);
			model.addAttribute("pjlist", list);
			TaskDTO taskDTO = new TaskDTO(searchVO.getPage(), projectService.count(searchVO));
			model.addAttribute("TaskDTO", taskDTO);
			return "project/prjt/pjList :: #prjtTable";
	}
	
	// 프로젝트 단건조회
	@GetMapping("prjtInfo/{prjtId}")
	public String prjtInfo(@PathVariable String prjtId, Model model) {
		ProjectVO projectVO = projectService.prjtInfo(prjtId);
		model.addAttribute("pjlist", projectVO);
		return "project/prjt/pjInfo";
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
			uri = "redirect:/prjtAllList";
		} else {
			uri = "test/test";
		}
		return uri;
	}

	
	/*
	// 프로젝트 수정 - 페이지
	@GetMapping("prjtUpdate")
	public String prjtUpdateForm(ProjectVO projectVO, Model model) {

		ProjectVO findVO = projectService.prjtInfo(prjtId);
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
	
	 */
}
