package com.samjo.app.approval.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.samjo.app.approval.service.AprVO;
import com.samjo.app.approval.service.DocService;
import com.samjo.app.approval.service.DocVO;
import com.samjo.app.common.service.PageDTO;
import com.samjo.app.common.service.SearchVO;

@Controller
public class DocController {
	
	DocService docService;
	
	@Autowired
	public DocController(DocService docService) {
		this.docService = docService;
	}
	
	@GetMapping("hjTest")
	public String test() {
		return "approval/doc/test";
	}
	
	@GetMapping("docList")
	public String docList(SearchVO searchVO, Model model) {
		if(searchVO.getPage() == 0) {
			searchVO.setPage(1);
		}
		List<DocVO> list = docService.docList(searchVO);
		model.addAttribute("list", list);
		PageDTO pageDTO = new PageDTO(searchVO.getPage(), docService.count());
		model.addAttribute("pageDTO", pageDTO);
		return "approval/doc/list";
	}
	
	@GetMapping("docInfo")
	public String docInfo(DocVO docVO, Model model) {
		DocVO findVO = docService.docInfo(docVO);
		model.addAttribute("doc", findVO);
		return "approval/doc/info";
	}
	
	@GetMapping("docInsert")
	public String dobInsertForm(Model model) {
		model.addAttribute("doc", new DocVO());
		model.addAttribute("aprs", new ArrayList<AprVO>());
		return "approval/doc/insert";
	}
	
	@PostMapping("docInsert")
	public String docInsertProcess(DocVO docVO) {
		int docNo = docService.docInfoInsert(docVO);
		if(docNo != -1) {
			return "redirect:docInfo?docNo=" + docNo;
		}
		return "test/test";
	}
	
	@GetMapping("docUpdate")
	public String docUpdateForm(@RequestParam Integer no, Model model) {
		DocVO docVO = new DocVO();
		docVO.setDocNo(no);
		DocVO findVO = docService.docInfo(docVO);
		model.addAttribute("doc", findVO);
		return "approval/doc/update";
	}
	
	@PostMapping("docUpdate")
	public String docUpdateProcess(DocVO docVO) {
		int ret = docService.docInfoUpdate(docVO);
		if(ret == 1) {
			return "redirect:docInfo?docNo=" + docVO.getDocNo();
		} else {
			return "test/test";
		}
	}
	
}
