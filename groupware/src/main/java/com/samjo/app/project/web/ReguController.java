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

import com.samjo.app.approval.service.DocVO;
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
	@GetMapping("cust/regu/insert")
	public String reguInsertForm(Model model) {
		
		EmpVO empVO = SecuUtil.getLoginEmp();
		
		if(empVO != null) {
			
			List<EmpVO> resp = deptService.myDeptMngrs(empVO); // 책임자.
			DeptVO dept = deptService.myDeptEmps(empVO.getDeptId()); // 우리부서 모든사원.
			List<ProjectVO> regus = reguService.reguStadList(empVO); // 기존상시업무목록.
			
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
	@PostMapping("cust/regu/insert")
	public String reguInsertProcess(ProjectVO projectVO, String flag) {
		int result = 0;
		
		// (flag == YES) => 기존 상시업무에 등록.
		if(flag.equals("YES")) {
			
			result = reguService.reguCommonInsert(projectVO);
			if(result > 0) {
				return "redirect:/cust/regu/info?taskNo=" + result;
			}
			
		} else if(flag.equals("NO")) {
			
			result = reguService.reguStadInsert(projectVO);
			if(result > 0) {
				return "redirect:/cust/regu/info?taskNo=" + result;
			}
			
		} 
		
		return "test/test";
	}
	
	// 단건조회
	@GetMapping("cust/regu/info")
	public String reguInfo(Model model, @RequestParam Integer taskNo) {
		
		EmpVO empVO = SecuUtil.getLoginEmp();
		
		if(empVO != null) {
			
			ProjectVO findVO = reguService.reguInfo(empVO.getCustNo(), taskNo);
			model.addAttribute("regu", findVO);
			
			List<DocVO> docs = reguService.taskDocList(taskNo);
			model.addAttribute("docs", docs);
			
			return "project/regu/info";
			
		} else {
			
			return "test/test";
		}
	}
	
	// 담당업무 완료
	@ResponseBody
	@PutMapping("cust/regu/info/ok/{taskNo}")
	public Map<String, Object> reguCmpltModify(@PathVariable Integer taskNo, 
												@RequestBody List<TaskEmpsVO> emps) {
		
		return reguService.reguCmpltModify(emps);
	}
	
	// 전체조회
	@GetMapping("cust/regu/list")
	public String reguTaskList(SearchVO searchVO, Model model) {
		
		checkSearch(searchVO);
		EmpVO empVO = SecuUtil.getLoginEmp();
		if(searchVO.getSortCondition().equals("tc.standard_no")) {
			searchVO.setSortCondition("tc.task_no DESC");
		}
		
		if(empVO != null) {
			
			List<ProjectVO> list = reguService.reguTaskList(empVO, searchVO);
			int count = reguService.countReguTasks(empVO, searchVO);
			PageDTO pageDTO = new PageDTO(searchVO.getPage(), count);
			
			model.addAttribute("list", list);
			model.addAttribute("pageDTO", pageDTO);
			model.addAttribute("search", searchVO);
			model.addAttribute("path", "cust/regu/list"); 
			
			return "project/regu/list.html";
			
		} else {
			
			return "test/test";
		}
		
	}
	
	// 전체조회(상위)
	@GetMapping("cust/regu/stadList")
	public String reguStadList(SearchVO searchVO, Model model) {
		
		checkSearch(searchVO);
		EmpVO empVO = SecuUtil.getLoginEmp();
		
		if(empVO != null) {
			
			List<ProjectVO> list = reguService.reguList(empVO, searchVO);
			int count = reguService.countRegus(empVO, searchVO);
			PageDTO pageDTO = new PageDTO(searchVO.getPage(), count);
			
			if(empVO.getPermId().equals("1C2c")) {
				List<DeptVO> depts = deptService.myCustDepts(empVO);				
				model.addAttribute("depts", depts);
			} 
			
			model.addAttribute("list", list);
			model.addAttribute("pageDTO", pageDTO);
			model.addAttribute("search", searchVO);
			model.addAttribute("path", "stadList"); 
			
			return "project/regu/stadList";				
			
		}
	
		return "test/test";
	}
	
	// 검색어/페이지 조회.
	@PostMapping("cust/regu/stadList/sch")
	public String reguStadSearchList(SearchVO searchVO, Model model) {
		
		checkSearch(searchVO);
		EmpVO empVO = SecuUtil.getLoginEmp();
		
		if(empVO != null) {
			
			System.out.println(searchVO);
			List<ProjectVO> list = reguService.reguList(empVO, searchVO);
			int count = reguService.countRegus(empVO, searchVO);
			PageDTO pageDTO = new PageDTO(searchVO.getPage(), count);
			
			if(empVO.getPermId().equals("1C2c")) {
				List<DeptVO> depts = deptService.myCustDepts(empVO);				
				model.addAttribute("depts", depts);
			} 
			
			model.addAttribute("list", list);
			model.addAttribute("pageDTO", pageDTO);
			model.addAttribute("search", searchVO);
			model.addAttribute("path", "stadList"); 
			
			return "project/regu/stadList :: #stadListArea";				
			
		}
	
		return "test/test";
	}
	
	// 단건조회(상위)
	@GetMapping("cust/regu/stadInfo")
	public String reguStadInfo(Model model, @RequestParam String reguId) {
		
		EmpVO empVO = SecuUtil.getLoginEmp();
		SearchVO search = new SearchVO();
		checkSearch(search);
		
		if(empVO != null) {
			
			ProjectVO regu = reguService.reguStadInfo(empVO, reguId);
			model.addAttribute("regu", regu);
			
			search.setKeywordCondition("regu_id");
			search.setKeyword(reguId);
			search.setSortCondition("tc.task_no DESC");
			List<ProjectVO> list = reguService.reguTaskList(empVO, search);
			model.addAttribute("tasks", list); 
			
			int count = reguService.countRegus(empVO, search);
			PageDTO pageDTO = new PageDTO(search.getPage(), count);
			model.addAttribute("pageDTO", pageDTO); 
			
			model.addAttribute("path", "stadInfo"); 
			
			return "project/regu/stadInfo";				
			
		}
	
		return "test/test";
	}

	@GetMapping("cust/regu/update")
	public String reguUpdate(Model model, @RequestParam String reguId) {
		EmpVO empVO = SecuUtil.getLoginEmp();
		
		if(empVO != null) {
			
			ProjectVO regu = reguService.reguStadInfo(empVO, reguId);
			model.addAttribute("regu", regu);
			
			return "project/regu/stadUpdate";
			
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
			searchVO.setSortCondition("tc.standard_no");
		}
		return searchVO;
	}

}
