package com.samjo.app.approval.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.samjo.app.approval.service.AprService;
import com.samjo.app.approval.service.AprVO;

@RestController
public class AprController {
	
	@Autowired
	AprService aprService;
	
	@PutMapping("apr/{no}/{id}")
	public int goToApr(@PathVariable Integer docNo, @PathVariable String draft) {
		AprVO aprVO = new AprVO();
		aprVO.setDocNo(docNo);
		aprVO.setAprEmp(draft);
		return aprService.goToApr(aprVO);
	}

}
