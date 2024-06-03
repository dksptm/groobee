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
import com.samjo.app.pay.service.ImportResVO;
import com.samjo.app.pay.service.PayService;
import com.samjo.app.pay.service.PayVO;
import com.samjo.app.pay.service.ReqPaymentScheduler;

@Controller
public class PayController {

	@Autowired
	PayService payservice;
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
	
	//결제 테스트
	@GetMapping("sol/payTest")
	public String payTestPage(Model model) throws ParseException {
		//String importVO= payservice.payLList();
		//model.addAttribute("portVO", importVO);
		return "solution/pay/payTest";
	}
	
	//결제 스케줄러 테스트
	@PostMapping("/payment")
	public @ResponseBody void getImportToken(@RequestParam Map<String, Object> map)
			throws JsonMappingException, JsonProcessingException {
		//long customer_uid = Integer.parseInt((String) map.get("customer_uid"));
		String customer_uid = (String) map.get("customer_uid");
		int price = Integer.parseInt((String) map.get("price"));
		long merchant_uid =  Long.parseLong((String) map.get("merchant_uid"));

		scheduler.startScheduler(customer_uid, price, merchant_uid);
		/*
		 * if(getPayementStatus.paymentStatus(merchant_uid).equals("paid")) {
		 * scheduler.startScheduler(customer_uid,price); }
		 */
	}
}
