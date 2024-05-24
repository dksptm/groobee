package com.samjo.app.ct.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.samjo.app.common.service.SearchVO;
import com.samjo.app.ct.service.CtDTO;
import com.samjo.app.ct.service.CtService;
import com.samjo.app.ct.service.CtVO;

@Controller
public class CtController {

	@Autowired
	CtService ctservice;
	
	//계약 전체조회
	@GetMapping("sol/ctList")
	public String ctListPage(SearchVO searchVO, Model model) {
		if (searchVO.getPage() <= 0) {
			searchVO.setPage(1);
		}
		if (searchVO.getCtSort() == null || searchVO.getCtSort().trim().isEmpty()) {
			searchVO.setCtSort("ct_no");
		}
		System.out.println(searchVO);
		List<CtVO> list = ctservice.ctList(searchVO);
		model.addAttribute("list", list);
		CtDTO ctDTO = new CtDTO(searchVO.getPage(), ctservice.count(searchVO));
		model.addAttribute("CtDTO", ctDTO);
		return "solution/ct/ctList";
	}
	
	//계약 조회페이지 검색/페이징 처리
	@PostMapping("sol/viewCtList")
	public String viewCtListPage(SearchVO searchVO, Model model) {
		System.out.println("searchVO: "+searchVO);
		System.out.println("startDay : "+ searchVO.getCtStart());
		if (searchVO.getPage() <= 0) {
			searchVO.setPage(1);
		}
		if (searchVO.getCtSort() == null || searchVO.getCtSort().trim().isEmpty()) {
			searchVO.setCtSort("ct_no");
		}
		List<CtVO> list = ctservice.ctList(searchVO);
		model.addAttribute("list", list);
		CtDTO ctDTO = new CtDTO(searchVO.getPage(), ctservice.count(searchVO));
		model.addAttribute("CtDTO", ctDTO);
		return "solution/ct/ctList :: #ctTable";
	}
	
	//계약 상세조회
	@GetMapping("sol/ctInfo/{ctNo}")
	public String ctInfoPage(@PathVariable int ctNo,Model model) {
		CtVO ctVO = new CtVO();
		ctVO.setCtNo(ctNo);
		model.addAttribute("ctVO", ctVO);
		return "solution/ct/ctInfo";
	}
}
