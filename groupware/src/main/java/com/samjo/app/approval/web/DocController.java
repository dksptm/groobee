package com.samjo.app.approval.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
import com.samjo.app.emp.service.EmpVO;
import com.samjo.app.project.service.ProjectVO;
import com.samjo.app.security.service.LoginUserVO;

@Controller
public class DocController {
	
	DocService docService;
	
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
	
	// 한 직원이 작성한 문서 전체 조회.
	@GetMapping("myDocList")
	public String docList(Model model,  Authentication authentication) {
		Object principal = authentication.getPrincipal();
		if (principal instanceof LoginUserVO) {
            LoginUserVO loginUserVO = (LoginUserVO) principal;
            String empId = loginUserVO.getEmpId();
            List<DocVO> list = docService.empDocList(empId);
            model.addAttribute("list", list);
            return "approval/list/empDocs";
        } else {
        	System.out.println("Not principal instanceof LoginUserVO");
        	return "test/test";
        }
	}
	
	// 로그인한 직원이 현재 결재해야할 문서리스트.
	@GetMapping("myAprList")
	public String aprList(Model model, Authentication authentication) {
		Object principal = authentication.getPrincipal();
		if (principal instanceof LoginUserVO) {
            LoginUserVO loginUserVO = (LoginUserVO) principal;
            String empId = loginUserVO.getEmpId();
            System.out.println("empId => " + empId);
            List<DocVO> list = docService.getMyAprList(empId);
            System.out.println("list =============> " + list + "============end.");
            model.addAttribute("list", list);
            return "approval/list/empAprs";
        } else {
        	System.out.println("Not principal instanceof LoginUserVO");
        	return "test/test";
        }		
	}
	
	// 문서 상세정보.
	@GetMapping("docInfo")
	public String docInfo(DocVO docVO, Model model) {
		DocVO findVO = docService.docInfo(docVO);
		List<EmpVO> emps = docService.docRefs(docVO.getDocNo());
		List<ProjectVO> tasks = docService.docTasks(docVO.getDocNo());
		model.addAttribute("doc", findVO);
		model.addAttribute("tasks", tasks);
		model.addAttribute("emps", emps);
		
		return "approval/doc/info";
	}
	
	// 문서작성 양식.
	@GetMapping("docInsert")
	public String dobInsertForm(Model model) {
		DocVO docVO = docService.getDocNo();
		List<TempVO> temps = docService.getCustTemps();
		model.addAttribute("doc", docVO);
		model.addAttribute("temps", temps);
		return "approval/doc/insert";
	}
	
	// 문서작성 저장.
	@PostMapping("docInsert")
	public String docInsertProcess(DocVO docVO) {
		System.out.println("draftName =====>" + docVO.getDraftName());
		int docNo = docService.docInfoInsert(docVO);
		if(docNo != -1) {
			return "redirect:docInfo?docNo=" + docNo;
		}
		return "test/test";
	}
	
	// 문서수정 양식
	@GetMapping("docUpdate")
	public String docUpdateForm(@RequestParam Integer no, Model model) {
		DocVO docVO = new DocVO();
		docVO.setDocNo(no);
		DocVO findVO = docService.docInfo(docVO);
		model.addAttribute("doc", findVO);
		
		List<TempVO> temps = docService.getCustTemps();
		List<EmpVO> emps = docService.docRefs(docVO.getDocNo());
		List<ProjectVO> tasks = docService.docTasks(docVO.getDocNo());
		model.addAttribute("temps", temps);
		model.addAttribute("emps", emps);
		model.addAttribute("tasks", tasks);
		
		System.out.println(findVO);
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
