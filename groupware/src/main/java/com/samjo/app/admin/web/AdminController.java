package com.samjo.app.admin.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.samjo.app.common.service.SearchVO;
import com.samjo.app.common.util.SecuUtil;
import com.samjo.app.ct.service.CtDTO;
import com.samjo.app.ct.service.CtService;
import com.samjo.app.ct.service.CtVO;
import com.samjo.app.emp.service.EmpVO;
import com.samjo.app.pay.service.PayService;
import com.samjo.app.pay.service.PayVO;

@Controller
public class AdminController {

	@Autowired
	CtService ctservice;
	@Autowired
	PayService payservice;
	
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
}
