package com.samjo.app.approval.web;

import java.util.Map;

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
	
	@PutMapping("aprGo/{docNo}")
	public Map<String, Object> goToApr(@PathVariable Integer docNo, @RequestBody AprVO aprVO) { 
		return aprService.goToApr(aprVO);
	}
	
	@PutMapping("aprOk/{docNo}")
	public Map<String, Object> aprOk(@PathVariable Integer docNo, @RequestBody AprVO aprVO) {
		return aprService.aprOk(aprVO);
	}
	
	@PutMapping("aprNg/{docNo}")
	public Map<String, Object> aprNg(@PathVariable Integer docNo, @RequestBody AprVO aprVO) {
		return aprService.aprNg(aprVO);
	}

}
