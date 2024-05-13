package com.samjo.app.approval.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.samjo.app.approval.service.AprService;
import com.samjo.app.approval.service.AprVO;

@RestController
public class AprController {
	
	@Autowired
	AprService aprService;
	
	@PutMapping("apr/{docNo}/{draft}")
	public int goToApr(@PathVariable Integer docNo, @PathVariable String draft, 
						@RequestBody AprVO aprVO) { 
		return aprService.goToApr(aprVO);
	}

}
