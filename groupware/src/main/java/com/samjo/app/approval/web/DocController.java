package com.samjo.app.approval.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.samjo.app.approval.service.DocService;
import com.samjo.app.approval.service.DocVO;
import com.samjo.app.approval.service.TempVO;
import com.samjo.app.common.service.PageDTO;
import com.samjo.app.common.service.SearchVO;

@Controller
public class DocController {
	
	DocService docService;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	public DocController(DocService docService) {
		this.docService = docService;
	}

	// 문서 전체조회.
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
	
	// 문서 상세정보.
	@GetMapping("docInfo")
	public String docInfo(DocVO docVO, Model model) {
		
		session.setAttribute("id", "E005");
		session.setAttribute("dept", "D002");
		session.setAttribute("cust", "C001");
		
		DocVO findVO = docService.docInfo(docVO);
		model.addAttribute("doc", findVO);
		return "approval/doc/info";
	}
	
	// 문서작성 양식.
	@GetMapping("docInsert")
	public String dobInsertForm(Model model) {
		
		session.setAttribute("id", "E005");
		session.setAttribute("dept", "D002");
		session.setAttribute("cust", "C001");
		
		DocVO docVO = docService.getDocNo();
		List<TempVO> temps = docService.getCustTemps();
		model.addAttribute("doc", docVO);
		model.addAttribute("temps", temps);
		return "approval/doc/insert";
	}
	
	// 문서작성 저장.
	@PostMapping("docInsert")
	public String docInsertProcess(DocVO docVO) {
		int docNo = docService.docInfoInsert(docVO);
		if(docNo != -1) {
			return "redirect:docInfo?docNo=" + docNo;
		}
		System.out.println(docVO);
		return "test/test";
	}
	
	// 문서수정 양식
	@GetMapping("docUpdate")
	public String docUpdateForm(@RequestParam Integer no, Model model) {
		session.setAttribute("id", "E005");
		session.setAttribute("dept", "D002");
		
		DocVO docVO = new DocVO();
		docVO.setDocNo(no);
		DocVO findVO = docService.docInfo(docVO);
		model.addAttribute("doc", findVO);
		return "approval/doc/update";
	}
	
	// 문서수정 반영.
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
