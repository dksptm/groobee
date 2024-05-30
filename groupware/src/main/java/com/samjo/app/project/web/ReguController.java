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

import com.samjo.app.common.service.PageDTO;
import com.samjo.app.common.service.SearchVO;
import com.samjo.app.common.util.SecuUtil;
import com.samjo.app.emp.service.DeptService;
import com.samjo.app.emp.service.DeptVO;
import com.samjo.app.emp.service.EmpVO;
import com.samjo.app.project.service.ProjectVO;
import com.samjo.app.project.service.ReguService;
import com.samjo.app.project.service.TaskEmpsVO;

@Controller
public class ReguController {
	
	ReguService reguService;
	DeptService deptService;
	
	@Autowired
	public ReguController(ReguService reguService, DeptService deptService) {
		this.reguService = reguService;
		this.deptService = deptService;
	}
	
	// 상시업무등록 - 양식.
	@GetMapping("reguInsert")
	public String reguInsertForm(Model model) {
		
		EmpVO empVO = SecuUtil.getLoginEmp();
		
		if(empVO != null) {
			
			List<EmpVO> resp = deptService.respMngrList(empVO.getCustNo()); // 책임자.
			DeptVO dept = deptService.myDeptEmps(empVO.getDeptId()); // 우리부서 모든사원.
			List<ProjectVO> regus = reguService.reguStadList(empVO.getCustNo()); // 기존상시업무목록.
			
			model.addAttribute("resp", resp);					
			model.addAttribute("dept", dept);					
			model.addAttribute("regus", regus);					
			model.addAttribute("taskRegu", new ProjectVO());
			
			return "project/regu/insert";
			
		} else {
			
			return "test/test";
		}
	}
	
	// 상시업무등록 - 등록.
	@PostMapping("reguInsert")
	public String reguInsertProcess(ProjectVO projectVO, String flag) {
		int result = 0;
		
		// (flag == YES) => 기존 상시업무에 등록.
		if(flag.equals("YES")) {
			
			result = reguService.reguCommonInsert(projectVO);
			if(result > 0) {
				return "redirect:reguInfo?taskNo=" + result;
			}
			
		} else if(flag.equals("NO")) {
			
			result = reguService.reguStadInsert(projectVO);
			if(result > 0) {
				return "redirect:reguInfo?taskNo=" + result;
			}
			
		} 
		
		return "test/test";
	}
	
	// 단건조회
	@GetMapping("reguInfo")
	public String reguInfo(Model model, @RequestParam Integer taskNo) {
		
		EmpVO empVO = SecuUtil.getLoginEmp();
		
		if(empVO != null) {
			
			ProjectVO findVO = reguService.reguInfo(empVO.getCustNo(), taskNo);
			model.addAttribute("regu", findVO);
			
			return "project/regu/info";
			
		} else {
			
			return "test/test";
		}
	}
	
	// 담당업무 완료
	@ResponseBody
	@PutMapping("reguInfo/ok/{taskNo}")
	public Map<String, Object> reguCmpltModify(@PathVariable Integer taskNo, 
												@RequestBody List<TaskEmpsVO> emps) {
		
		return reguService.reguCmpltModify(emps);
	}
	
	// 전체조회
	@GetMapping("reguList")
	public String reguTaskList(SearchVO searchVO, Model model) {
		
		checkSearch(searchVO);
		EmpVO empVO = SecuUtil.getLoginEmp();
		
		if(empVO != null) {
			
			List<ProjectVO> list = reguService.reguTaskList(empVO.getCustNo(), searchVO);
			int count = reguService.countReguTasks(empVO.getCustNo(), searchVO);
			PageDTO pageDTO = new PageDTO(searchVO.getPage(), count);
			
			model.addAttribute("list", list);
			model.addAttribute("pageDTO", pageDTO);
			model.addAttribute("search", searchVO);
			model.addAttribute("path", "reguList"); 
			
			return "project/regu/list.html";
			
		} else {
			
			return "test/test";
		}
		
	}
	
	// SearchVO check
	public SearchVO checkSearch(SearchVO searchVO) {
		if(searchVO.getPage() == 0) {
			searchVO.setPage(1);
		}
		if(searchVO.getSortCondition() == null) {
			searchVO.setSortCondition("tc.task_no DESC");
		}
		return searchVO;
	}

}
