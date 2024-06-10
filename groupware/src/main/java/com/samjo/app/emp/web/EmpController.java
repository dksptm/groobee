package com.samjo.app.emp.web;

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

import com.samjo.app.common.service.PageDTO;
import com.samjo.app.common.service.SearchVO;
import com.samjo.app.common.util.SecuUtil;
import com.samjo.app.cust.service.CustVO;
import com.samjo.app.emp.service.DeptVO;
import com.samjo.app.emp.service.EmpService;
import com.samjo.app.emp.service.EmpVO;
import com.samjo.app.emp.service.JobVO;

@Controller
public class EmpController {
	
	@Autowired
	EmpService empService;
	
	// 솔루션 - 최초계정 등록.
	@ResponseBody
	@PostMapping("sol/customer/firstEmp")
	private Map<String, Object> insertFirstEmp(@RequestBody EmpVO empVO) {
		return empService.insertFirstEmp(empVO);
	}
	
	// 솔루션 - 고객사 소속 계정 전체조회 시 검색기능.
	@PostMapping("sol/customer/emps")
	public String custEmpList(SearchVO search, String custNo, Model model) {
		if(search.getPage() == 0) {
			search.setPage(1);
		}
		List<EmpVO> list = empService.selectEmpAll(custNo, search);
		int count = empService.countEmpAll(custNo, search);	
		PageDTO pageDTO = new PageDTO(search.getPage(), count);
		model.addAttribute("list", list);
		model.addAttribute("pageDTO", pageDTO);
		
		return "solution/cust/modal :: #EmpListArea";
	}
	
	// 관리자 - 사원등록 - form
	@GetMapping("cust/admin/empInsert")
	public String insertEmpForm(Model model) {
		
		EmpVO empVO = SecuUtil.getLoginEmp();
		
		CustVO cust = empService.empMaxCur(empVO.getCustNo());
		model.addAttribute("cust", cust);
		
		List<DeptVO> depts = empService.insertFormDepts(empVO);
		List<JobVO> jobs = empService.insertFormJobs(empVO);
		
		model.addAttribute("depts", depts);
		model.addAttribute("jobs", jobs);
		model.addAttribute("emp", new EmpVO());
		
		return "manager/emp_insert";
	}
	
	// 사번체크.
	@ResponseBody
	@GetMapping("cust/admin/idCheck")
	public int idCheck(@RequestParam("eno") String empId, @RequestParam("cno") String custNo) {
		int result = empService.idCheck(empId, custNo);
		return result;
	}
	
	// 관리자 - 사원등록 - process
	@PostMapping("cust/admin/empInsert")
	public String insertEmpProcess(EmpVO empVO) {
		
		String eid = empService.insertEmp(empVO);
		if(eid.equals("")) {
			return "test/test";	
		}
		
		return "redirect:/cust/emp/info?eid=" + eid;	
	}
	
	// 관리자,본인 - 사원단건조회
	@GetMapping("cust/emp/info")
	public String empInfo(String eid, Model model) {
		
		if(eid == null) {
			return "test/test";
		}
		
		EmpVO empVO = SecuUtil.getLoginEmp();
		
		if(empVO == null) {
			return "test/test";
		}
		
		EmpVO findVO = null;
		if(empVO.getPermId().equals("1C2c") || empVO.getPermId().equals("1C1c")) {
			findVO = empService.selectEmpInfo(eid, empVO.getCustNo());			
		} else {
			findVO = empService.selectEmpInfo(empVO.getEmpId(), empVO.getCustNo());				
		}
		
		if(findVO == null) {
			return "test/test";
		}
		
		model.addAttribute("emp", findVO);
		return "emp/emp_info";
	}
	
	// 관리자 - 사원수정 - form
	@GetMapping("cust/admin/empUpdate")
	public String updateEmpForm(String eid, Model model) {
		
		if(eid == null) {
			return "test/test";
		}
		
		EmpVO empVO = SecuUtil.getLoginEmp();		
		if(empVO == null) {
			return "test/test";
		}
		
		EmpVO findVO = null;
		if(empVO.getPermId().equals("1C2c")) {
			findVO = empService.selectEmpInfo(eid, empVO.getCustNo());			
		} else {
			findVO = empService.selectEmpInfo(empVO.getEmpId(), empVO.getCustNo());				
		}
		
		if(findVO == null) {
			return "test/test";
		}
		
		model.addAttribute("emp", findVO);
		
		List<DeptVO> depts = empService.insertFormDepts(empVO);
		List<JobVO> jobs = empService.insertFormJobs(empVO);
		
		model.addAttribute("depts", depts);
		model.addAttribute("jobs", jobs);
		
		return "manager/emp_update";
	}
	
	// 관리자 - 사원수정 - process
	@PostMapping("cust/admin/empUpdate")
	public String updateEmpProcess(EmpVO empVO) {
		
		String eid = empService.updateEmp(empVO);
		if(eid.equals("NG")) {
			return "test/test";	
		}
		
		return "redirect:/cust/emp/info?eid=" + eid;
	}
	
	// 관리자 - 사원목록
	@GetMapping("cust/admin/empList")
	public String myEmpList(SearchVO search, Model model) {
		
		if(search.getPage() == 0) {
			search.setPage(1);
		}
		
		EmpVO empVO = SecuUtil.getLoginEmp();
		if(empVO == null || !empVO.getPermId().equals("1C2c")) {
			return "test/test";
		}
		
		List<EmpVO> list = empService.selectEmpAll(empVO.getCustNo(), search);
		int count = empService.countEmpAll(empVO.getCustNo(), search);	
		PageDTO pageDTO = new PageDTO(search.getPage(), count);
		model.addAttribute("list", list);
		model.addAttribute("pageDTO", pageDTO);
		
		CustVO cust = empService.empMaxCur(empVO.getCustNo());
		model.addAttribute("cust", cust);
		
		return "manager/emp_list";
	}
	
	// 관리자 - 사원목록 - 검색.
	@PostMapping("cust/admin/empList/sch")
	public String myEmpListSch(SearchVO search, Model model) {
		
		if(search.getPage() == 0) {
			search.setPage(1);
		}
		
		EmpVO empVO = SecuUtil.getLoginEmp();
		if(empVO == null || !empVO.getPermId().equals("1C2c")) {
			return "test/test";
		}
		
		List<EmpVO> list = empService.selectEmpAll(empVO.getCustNo(), search);
		int count = empService.countEmpAll(empVO.getCustNo(), search);	
		PageDTO pageDTO = new PageDTO(search.getPage(), count);
		model.addAttribute("list", list);
		model.addAttribute("pageDTO", pageDTO);
		
		return "solution/cust/modal :: #EmpListArea";
	}
	
	
	
	
	
	
	
	
	
	
	
}
