package com.samjo.app.admin.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.samjo.app.common.service.SearchVO;
import com.samjo.app.common.util.SecuUtil;
import com.samjo.app.ct.service.CtDTO;
import com.samjo.app.ct.service.CtService;
import com.samjo.app.ct.service.CtVO;
import com.samjo.app.emp.service.EmpVO;
import com.samjo.app.emp.service.JobService;
import com.samjo.app.emp.service.JobVO;
import com.samjo.app.pay.service.PayService;
import com.samjo.app.pay.service.PayVO;
import com.samjo.app.security.service.LoginUserVO;

@Controller
public class AdminController {

	@Autowired
	CtService ctservice;
	@Autowired
	PayService payservice;
	@Autowired
	JobService jobService;
	
	//결제·계약조회
	@GetMapping("cust/admin/payAndCt")
	public String payCtPage(SearchVO searchVO, Model model) {
		EmpVO empVO = SecuUtil.getLoginEmp();
		List<CtVO> list = ctservice.custCtList(empVO.getCustNo());
		model.addAttribute("ctList", list);
		if (searchVO.getPage() <= 0) {
			searchVO.setPage(1);
		}
		searchVO.setCustNo(empVO.getCustNo());
		List<PayVO> payList = payservice.custPayList(searchVO);
		model.addAttribute("list",payList);
		CtDTO payDTO = new CtDTO(searchVO.getPage(), payservice.custCount(empVO.getCustNo()));
		model.addAttribute("payDTO", payDTO);
		return "admin/payAndCtList";
	}
	
	//직급관리 전체조회 
	@GetMapping("cust/admin/jobList")
	public String empJobList(Model model, Authentication authentication) {
		Object principal = authentication.getPrincipal();
		if (principal instanceof LoginUserVO) {
				LoginUserVO loginUserVO = (LoginUserVO) principal;
				String custNo = loginUserVO.getCustNo();
				
		JobVO jobVO = new JobVO();
		jobVO.setCustNo(custNo);
		
		//해당 고객사가 관리하는 직급 정보를 리스트에 담아 보낸다
		List<JobVO> list = jobService.getJobList(jobVO);
		model.addAttribute("getJobList", list);
		}
		return "admin/jobList";	
	}
	
	@ResponseBody
	@PutMapping("cust/admin/JobInsert")
	public String JobInsert(@RequestBody JobVO jobVO, Model model) {
		//Object principal = authentication.getPrincipal();
//		if (principal instanceof LoginUserVO) {
//				LoginUserVO loginUserVO = (LoginUserVO) principal;
//				String custNo = loginUserVO.getCustNo();
//				
//		jobVO.setCustNo(custNo);
		int jNo = jobService.jobInsert(jobVO);
		String uri = "admin/JobList";
		
		if (jNo > -1) {
			uri = "true";
		} else {
			uri = "false";
		}
		return uri;
	}
	
	@ResponseBody
	@PutMapping("cust/admin/JobUpdate")
	public String JobUpdate(@RequestBody JobVO jobVO) {
		
		return "admin/JobList";
	}
	
	@ResponseBody
	@DeleteMapping("cust/admin/JobDelete")
	public String JobDelete(@RequestBody JobVO jobVO) {
	
		return "admin/JobList";
	}
	
}
