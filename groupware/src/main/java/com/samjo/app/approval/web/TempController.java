package com.samjo.app.approval.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.samjo.app.approval.service.TempService;
import com.samjo.app.approval.service.TempVO;

@Controller
public class TempController {
	
	TempService tempService;
	
	@Autowired
	public TempController(TempService tempService) {
		this.tempService = tempService;
	}
	
	// 휴가원 저장.
	@ResponseBody
	@PostMapping("cust/pto")
	public Map<String, Object> ptoInsertProcess(@RequestBody TempVO pto) {
		return tempService.insertPto(pto);
	}
	
	// 휴가원 단건조회.
	@ResponseBody
	@GetMapping("cust/pto/{docNo}")
	public TempVO ptoInsertProcess(@PathVariable Integer docNo) {
		return tempService.selectPto(docNo);
	}
	
	/*
	 * // 문서수정 양식
	 * 
	 * @GetMapping("docUpdate") public String docUpdateForm(@RequestParam Integer
	 * no, Model model) { DocVO docVO = new DocVO(); docVO.setDocNo(no); DocVO
	 * findVO = docService.docInfo(docVO); model.addAttribute("doc", findVO);
	 * 
	 * List<TempVO> temps = docService.getCustTemps(); List<EmpVO> emps =
	 * docService.docRefs(docVO.getDocNo()); List<ProjectVO> tasks =
	 * docService.docTasks(docVO.getDocNo()); model.addAttribute("temps", temps);
	 * model.addAttribute("emps", emps); model.addAttribute("tasks", tasks);
	 * 
	 * System.out.println(findVO); return "approval/doc/update"; }
	 * 
	 * // 문서수정 반영.
	 * 
	 * @PostMapping("docUpdate") public String docUpdateProcess(DocVO docVO) { int
	 * ret = docService.docInfoUpdate(docVO); if(ret == 1) { return
	 * "redirect:docInfo?docNo=" + docVO.getDocNo(); } else { return "test/test"; }
	 * }
	 */
	
}
