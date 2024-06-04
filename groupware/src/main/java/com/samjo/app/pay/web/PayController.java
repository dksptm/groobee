package com.samjo.app.pay.web;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.samjo.app.common.service.SearchVO;
import com.samjo.app.ct.service.CtDTO;
import com.samjo.app.ct.service.CtService;
import com.samjo.app.ct.service.CtVO;
import com.samjo.app.pay.service.PayService;
import com.samjo.app.pay.service.PayVO;
import com.samjo.app.pay.service.ReqPaymentScheduler;

@Controller
public class PayController {

	@Autowired
	PayService payservice;
	@Autowired
	CtService ctservice;
	@Autowired
	ReqPaymentScheduler scheduler;
	
	//결제내역 조회
	@GetMapping("sol/payList")
	public String payListPage(SearchVO searchVO, Model model) {
		if (searchVO.getPage() <= 0) {
			searchVO.setPage(1);
		}
		if (searchVO.getPaySort() == null || searchVO.getPaySort().trim().isEmpty()) {
			searchVO.setPaySort("pay_no");
		}
		List<PayVO> list = payservice.payList(searchVO);
		model.addAttribute("list",list);
		CtDTO payDTO = new CtDTO(searchVO.getPage(), payservice.count(searchVO));
		model.addAttribute("payDTO", payDTO);
		return "solution/pay/payList";
	}
	//결제내역 조회 페이징처리
	@PostMapping("sol/viewPayList")
	public String viewPayListPage(SearchVO searchVO, Model model) {
		if (searchVO.getPage() <= 0) {
			searchVO.setPage(1);
		}
		List<PayVO> list = payservice.payList(searchVO);
		System.out.println(list);
		model.addAttribute("list",list);
		CtDTO payDTO = new CtDTO(searchVO.getPage(), payservice.count(searchVO));
		model.addAttribute("payDTO", payDTO);
		return "solution/pay/payList :: #payTable";
	}
	
	//결제 테스트
	@GetMapping("sol/payTest")
	public String payTestPage(Model model) throws ParseException {
		//String importVO= payservice.payLList();
		//model.addAttribute("portVO", importVO);
		return "solution/pay/payTest";
	}
	
	//결제 스케줄러
	@PostMapping("/payment")
	@ResponseBody 
	public void getImportToken(@RequestParam Map<String, Object> map)
			throws JsonMappingException, JsonProcessingException {
		int ctNo = Integer.parseInt((String) map.get("ct_no"));
		String customer_uid = (String) map.get("customer_uid");
		int price = Integer.parseInt((String) map.get("price"));
		long merchant_uid =  Long.parseLong((String) map.get("merchant_uid"));

		scheduler.startScheduler(customer_uid, price, merchant_uid, ctNo);
		CtVO ctVO = new CtVO();
		ctVO.setCtNo(ctNo);
		ctVO.setPayCheck(1);
		ctservice.ctPayCheck(ctVO);
	}
}
