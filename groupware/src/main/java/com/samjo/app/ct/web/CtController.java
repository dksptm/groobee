package com.samjo.app.ct.web;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.samjo.app.common.service.SearchVO;
import com.samjo.app.ct.service.CtDTO;
import com.samjo.app.ct.service.CtService;
import com.samjo.app.ct.service.CtVO;
import com.samjo.app.solmodule.service.ModuleVO;

@Controller
public class CtController {

	@Autowired
	CtService ctservice;

	// 계약 전체조회
	@GetMapping("sol/ctList")
	public String ctListPage(SearchVO searchVO, Model model) {
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
		return "solution/ct/ctList";
	}

	// 계약 조회페이지 검색/페이징 처리
	@PostMapping("sol/viewCtList")
	public String viewCtListPage(SearchVO searchVO, Model model) {
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

	// 계약 상세조회
	@GetMapping("sol/ctInfo/{ctNo}")
	public String ctInfoPage(@PathVariable int ctNo, Model model) {
		CtVO ctVO = ctservice.ctInfo(ctNo);
		model.addAttribute("ctVO", ctVO);
		return "solution/ct/ctInfo";
	}

	//계약 등록
	@GetMapping("sol/ctInsert")
	public String ctInsert(Model model) {
		List<ModuleVO> modlist = ctservice.modList();
		model.addAttribute("modlist", modlist);
		CtVO ctVO = new CtVO();
		model.addAttribute("ctVO", ctVO);
		return "solution/ct/ctInsert";
	}
	
	// 계약 수정 화면
	@GetMapping("sol/ctUpdate/{ctNo}")
	public String ctUpdatePage(@PathVariable int ctNo, Model model) {
		List<ModuleVO> modlist = ctservice.modList();
		model.addAttribute("modlist", modlist);
		CtVO ctVO = ctservice.ctInfo(ctNo);
		model.addAttribute("ctVO", ctVO);
		return "solution/ct/ctUpdate";
	}

	// 계약 수정 처리
	@PostMapping("sol/ctUpdateProcess/{ctNo}")
	@ResponseBody
	public String ctUpdateProcess(@PathVariable int ctNo, CtVO ctVO, String[] modIds, Model model) {
		//ctservice.modUpdate(ctVO, modIds);
		System.out.println("CT : " + ctVO);
		System.out.println("modIds: " + Arrays.toString(modIds));
		ctservice.ctUpdate(ctVO, modIds);
		return "Success";
	}
	
}
