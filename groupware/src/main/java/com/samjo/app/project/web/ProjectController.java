package com.samjo.app.project.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.samjo.app.emp.service.DeptService;
import com.samjo.app.emp.service.DeptVO;
import com.samjo.app.emp.service.EmpVO;
import com.samjo.app.project.service.ProjectService;
import com.samjo.app.project.service.ProjectVO;
import com.samjo.app.security.service.LoginUserVO;

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
	public String prjtInsertForm(Model model, Authentication authentication) {
		
		EmpVO empVO = getLoginEmp(authentication);
		if(empVO != null) {
			List<EmpVO> list = deptService.respMngrList(empVO.getCustNo());
			model.addAttribute("mngr", list);
			System.out.println(list);
			model.addAttribute("projects", new ProjectVO());
			return "project/prjt/insert";			
		} else {
			return "test/test";
		}
		
	}
	@ResponseBody
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
	
	//  프로젝트 수정 - 페이지 
	@GetMapping("prjtUpdate")
	public String prjtUpdateForm(@RequestParam(required = false) String prjtId, Model model) {
		ProjectVO projectVO = new ProjectVO();
		projectVO.setPrjtId(prjtId);
		
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
	public String taskAllList(Model model) {
				List<ProjectVO> list = projectService.taskAllList();
				model.addAttribute("task", list);
				return "project/task/list";
			}
	// 프로젝트(하위) 업무 등록
	@GetMapping("taskInsert")
	public String taskInsertForm(Model model) {
		List<DeptVO> list = deptService.deptAllList();
		model.addAttribute("dept", list);
		
		model.addAttribute("task", new ProjectVO());
			return "project/task/insert";
			}
			
	@PostMapping("taskInsert")
	public String taskInsertProcess(ProjectVO projectVO) {
				int taskNo = projectService.taskInsert(projectVO);
				String uri = null;
				
				if(taskNo > -1) {
					uri = "redirect:taskInfo?taskNo=" + taskNo;
				} else {
					uri = "taskAllList";
				}
				return uri;
			}
	
	// 프로젝트 업무 등록시 부서 전체 목록. 
	/*@GetMapping("deptAll") 
	public String deptAll(Model model) {
		List<DeptVO> list = deptService.deptAllList();
		model.addAttribute("dept", list);
		return "project/task/insert";
	}*/
	
	// 프로젝트(하위) 업무 단건
	@GetMapping("taskInfo")
	public String taskInfo(ProjectVO projectVO, Model model) {
				ProjectVO findVO = projectService.taskInfo(projectVO);
				model.addAttribute("task", findVO);
				return "project/task/list";
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
	
	// 공통으로 사용
	public EmpVO getLoginEmp(Authentication authentication) {
		Object principal = authentication.getPrincipal();
		if (principal instanceof LoginUserVO) {
            LoginUserVO loginUserVO = (LoginUserVO) principal;
            
            String empId = loginUserVO.getEmpId();
            String custNo = loginUserVO.getCustNo();
            String deptId = loginUserVO.getDeptId();
            String permId = loginUserVO.getPermId();	
            
            EmpVO empVO = new EmpVO(empId, custNo, deptId, permId);
            return empVO;
        } else {
        	return null;
        }
	}
	

}
