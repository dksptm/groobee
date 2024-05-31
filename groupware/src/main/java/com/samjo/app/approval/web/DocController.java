package com.samjo.app.approval.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.samjo.app.approval.service.DocService;
import com.samjo.app.approval.service.DocVO;
import com.samjo.app.approval.service.TempVO;
import com.samjo.app.common.service.PageDTO;
import com.samjo.app.common.service.SearchVO;
import com.samjo.app.common.util.SecuUtil;
import com.samjo.app.emp.service.EmpVO;
import com.samjo.app.project.service.ProjectVO;
import com.samjo.app.upload.service.FileUploadService;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * 전자결재 중 문서관리.
 * @author 권효주.
 * 
 *
 */
@Controller
@Data
@RequiredArgsConstructor
public class DocController {
	
	final DocService docService;
	final FileUploadService fileUploadService;
	
	/* 위에서 @Data.@RequiredArgsConstructor 하고 필드에 final하면 @Autowired 안해도됨.
	 * @Autowired public DocController(DocService docService, FileUploadService
	 * fileUploadService) { this.docService = docService; this.fileUploadService =
	 * fileUploadService; }
	 */
	
	// 문서 전체조회.
	@GetMapping("docList")
	public String docList(SearchVO searchVO, Model model) {
		searchVO = checkSearch(searchVO);
		
		List<DocVO> list = docService.docList(searchVO);
		model.addAttribute("list", list);
		
		PageDTO pageDTO = new PageDTO(searchVO.getPage(), docService.count());
		model.addAttribute("pageDTO", pageDTO);
		
		return "approval/doc/list";
	}
	
	// 한 직원이 작성한 문서 전체 조회.
	@GetMapping("cust/doc/mydoc")
	public String myDocList(SearchVO searchVO, Model model) {
		
		searchVO = checkSearch(searchVO);
		EmpVO empVO = SecuUtil.getLoginEmp();
		
		if(empVO != null) {
			
			List<DocVO> list = docService.getMyDocList(empVO.getEmpId(), searchVO);
	        PageDTO pageDTO = new PageDTO(searchVO.getPage(), docService.countEmpDocs(empVO.getEmpId(), searchVO));
	        
	        model.addAttribute("list", list);
	        model.addAttribute("pageDTO", pageDTO);
	        model.addAttribute("search", searchVO);
	        model.addAttribute("path", "cust/doc/mydoc");
	        
            return "approval/list/empDocs";
            
        } else {
        	
        	return "test/test";
        }
	}
	
	// 한 직원이 현재 결재해야할 문서리스트.
	@GetMapping("myAprList")
	public String myAprList(SearchVO searchVO, Model model) {

		searchVO = checkSearch(searchVO);
		EmpVO empVO = SecuUtil.getLoginEmp();
		
		if (empVO != null) {
            
            List<DocVO> list = docService.getMyAprList(empVO.getEmpId(), searchVO);
            PageDTO pageDTO = new PageDTO(searchVO.getPage(), docService.countEmpApr(empVO.getEmpId(), searchVO));
            
            model.addAttribute("list", list);
            model.addAttribute("pageDTO", pageDTO);
            model.addAttribute("search", searchVO);
            model.addAttribute("path", "myAprList");
            
            return "approval/list/empAprs";
            
        } else {
        	
        	return "test/test";
        }		

	}
	
	// 전체문서 중 결재진행 중 문서.
	@GetMapping("docIng")
	public String docIng(SearchVO searchVO, Model model) {
		
		searchVO = checkSearch(searchVO);
		EmpVO empVO = SecuUtil.getLoginEmp();
		
		if(empVO != null) {
			
            List<DocVO> list = docService.getIngDocList(empVO, searchVO);
            PageDTO pageDTO = new PageDTO(searchVO.getPage(), docService.countIng(empVO, searchVO));
            
            
            model.addAttribute("list", list);
            model.addAttribute("pageDTO", pageDTO);
            model.addAttribute("search", searchVO);
            model.addAttribute("path", "docIng"); 
            
            return "approval/list/ing";
            
        } else {

        	return "test/test";
        }
		
	}
	
	// 전체문서 중 결재완료/반려 문서.
	@GetMapping("docCmplt")
	public String docCmplt(SearchVO searchVO, Model model) {
		
		searchVO = checkSearch(searchVO);
		EmpVO empVO = SecuUtil.getLoginEmp();
		
		if(empVO != null) {
			
			List<DocVO> list = docService.getCmpltDocList(empVO, searchVO);
			PageDTO pageDTO = new PageDTO(searchVO.getPage(), docService.countCmplt(empVO, searchVO));
			
			model.addAttribute("list", list);
			model.addAttribute("pageDTO", pageDTO);
			model.addAttribute("search", searchVO);
			model.addAttribute("path", "docCmplt"); 
			
			return "approval/list/cmplt";
			
		} else {
			
			return "test/test";
		}
		
	}
	
	// 문서 상세정보.
	@GetMapping("docInfo")
	public String docInfo(DocVO docVO, SearchVO searchVO, Model model) {
		
		searchVO = checkSearch(searchVO);
		DocVO findVO = docService.docInfo(docVO);
		List<EmpVO> refs = docService.docRefs(docVO.getDocNo());
		List<ProjectVO> tasks = docService.docTasks(docVO.getDocNo());
		
		model.addAttribute("doc", findVO);
		model.addAttribute("tasks", tasks);
		model.addAttribute("emps", refs);
		model.addAttribute("search", searchVO);
		
		return "approval/doc/info";
		
	}
	
	// 문서작성 -양식.
	@GetMapping("cust/doc/insert")
	public String dobInsertForm(Model model) {
		
		List<TempVO> temps = docService.getCustTemps();
		
		model.addAttribute("doc", new DocVO());
		model.addAttribute("temps", temps);
		
		return "approval/doc/insert";
		
	}
	
	// 문서작성 -저장.
	@PostMapping("cust/doc/insert")
	public String docInsertProcess(DocVO docVO, MultipartFile[] filelist) {
		
		// 첨부파일 디렉토리 저장부터.
		List<Map<String, Object>> fileInfoList = new ArrayList<Map<String, Object>>();
		if(!filelist[0].isEmpty()) {
			fileInfoList = fileUploadService.uploadFileInfo(filelist);			
		}
		
		// 파일저장 트랜잭션으로.
		int result = docService.docInfoInsert(docVO, fileInfoList);
		
		if(result == -1) {
			System.out.println("fail - doc table insert");
			return "test/test";
			
		} else if (result == 0) {
			System.out.println("fail - doc related table insert");
			return "test/test";
			
		} else {
			
			return "redirect:docInfo?docNo=" + result;
		}
		
	}
	
	// 문서수정 -양식
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
		
		return "approval/doc/update";
	}
	
	// 문서수정 -반영.
	@PostMapping("docUpdate")
	public String docUpdateProcess(DocVO docVO, MultipartFile[] filelist, String flag) {
		
		// (flag == YES) => 기존 첨부파일 삭제.
		if(flag.equals("YES")) {
			List<String> savaFileNames = docService.getDocFileSavaNames(docVO);
			if (savaFileNames.size() > 0) {
				fileUploadService.deleteFileInfo(savaFileNames);
			}
		}
		
		// 첨부파일 디렉토리 저장.
		List<Map<String, Object>> fileInfoList = new ArrayList<Map<String, Object>>();
		
		System.out.println(filelist == null);
		
		if(filelist != null && !filelist[0].isEmpty()) {
			fileInfoList = fileUploadService.uploadFileInfo(filelist);			
		}
		
		// 파일수정 트랜잭션으로.
		int result = docService.docInfoUpdate(docVO, fileInfoList, flag);
		
		if(result == -1) {
			System.out.println("fail - doc table update");
			return "test/test";
			
		} else if (result == 0) {
			System.out.println("fail - doc related table delete & insert");
			return "test/test";
			
		} else {
			
			return "redirect:docInfo?docNo=" + result;
		}
		
	}
	
	// 문서삭제
	@ResponseBody
	@DeleteMapping("cust/doc/delet/{docNo}")
	public Map<String, Object> deleteDoc(@PathVariable Integer docNo, 
												@RequestBody DocVO docVO) {
		
		// 실제 첨부파일 삭제.
		List<String> savaFileNames = docService.getDocFileSavaNames(docVO);
		if (savaFileNames.size() > 0) {
			fileUploadService.deleteFileInfo(savaFileNames);
		}
		
		return docService.deleteDoc(docVO);
	}
	
	public SearchVO checkSearch(SearchVO searchVO) {
		if(searchVO.getPage() == 0) {
			searchVO.setPage(1);
		}
		if(searchVO.getSortCondition() == null) {
			searchVO.setSortCondition("d.doc_no DESC");
		}
		if(searchVO.getAprStatCondition() == null || searchVO.getAprStatCondition().equals("aprStatAll")) {
			searchVO.setAprStatCondition("_");
		}
		if(searchVO.getDocStatCondition() == null || searchVO.getDocStatCondition().equals("docStatAll")) {
			searchVO.setDocStatCondition("_");
		}
		if(searchVO.getDraftStatCondition() == null || searchVO.getDraftStatCondition().equals("draftStatAll")) {
			searchVO.setDraftStatCondition("_");
		}
		return searchVO;
	}
	
	
}
