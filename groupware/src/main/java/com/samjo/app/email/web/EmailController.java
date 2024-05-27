package com.samjo.app.email.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.samjo.app.common.service.PageDTO;
import com.samjo.app.common.service.SearchVO;
import com.samjo.app.common.util.SecuUtil;
import com.samjo.app.email.service.EmailDTO;
import com.samjo.app.email.service.EmailService;
import com.samjo.app.email.service.EmailVO;
import com.samjo.app.emp.service.EmpVO;
import com.samjo.app.security.service.LoginUserVO;
import com.samjo.app.upload.service.EmailFileUploadService;

@Controller
public class EmailController {
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	EmailFileUploadService emailFileUploadService;
	
	// 받은메일 전체조회
	@GetMapping("inboxList")
	public String inboxList(SearchVO searchVO, Model model) {
		//페이지 세팅
		if(searchVO.getPage() <= 0) {
			searchVO.setPage(1);
		}
		if(searchVO.getEmSort() == null || searchVO.getEmSort().trim().isEmpty()) {
			searchVO.setEmSort("rec_email_no");
		}
		//받은 메일의 수신자. 접속중인 계정 정보 받아오기.
		EmpVO empVO = SecuUtil.getLoginEmp();
		
		//접속중인 계정의 id와, 고객사id 받아오기.
		String custNo = empVO.getCustNo();
		String recp = empVO.getEmpId();
		
		//searchVO에 담기.
		searchVO.setRecp(recp);
		searchVO.setCustNo(custNo);
		
		//매퍼 쿼리에 searchVO를 실어 보내고, 결과를 받아 list에 담기.
		List<EmailVO> list = emailService.inboxList(searchVO);
		
		//이 list는 타임리프로 보낼 것이므로, model에 담기.
		model.addAttribute("list", list);
		
		//PageDTO에 해당하는 EmailDTO에 
		EmailDTO emailDTO = new EmailDTO(searchVO.getPage(), emailService.countMyInbox(searchVO)); 
		model.addAttribute("EmailDTO", emailDTO);
        model.addAttribute("searchVO", searchVO);
		
		return "email/inboxList";
	}
	
    // 받은메일 검색/페이징 처리 AJAX 받는 걸로 바꿔보기
    @PostMapping("viewInboxList")
    public String viewInboxPage(@RequestBody SearchVO searchVO, Model model) {
        if (searchVO.getPage() <= 0) {
            searchVO.setPage(1);
        }
        EmpVO empVO = SecuUtil.getLoginEmp();
        String custId = empVO.getCustNo();
        String eid = empVO.getEmpId();
        searchVO.setRecp(eid);
        searchVO.setCustNo(custId);
        List<EmailVO> list = emailService.inboxList(searchVO);
        model.addAttribute("list", list);
        EmailDTO emailDTO = new EmailDTO(searchVO.getPage(), emailService.countMyInbox(searchVO)); 
        model.addAttribute("EmailDTO", emailDTO);
        
        return "email/inboxList :: #inboxTable";
    }
	
	
	// 받은메일 검색/페이징 처리
//	@PostMapping("viewInboxList")
//	public String viewInboxPage( SearchVO searchVO, Model model) {
//		if (searchVO.getPage() <= 0) {
//			searchVO.setPage(1);
//		}
//		EmpVO empVO = SecuUtil.getLoginEmp();
//		String custId = empVO.getCustNo();
//		String eid = empVO.getEmpId();
//		searchVO.setRecp(eid);
//		searchVO.setCustNo(custId);
//		List<EmailVO> list = emailService.inboxList(searchVO);
//		model.addAttribute("list", list);
//		EmailDTO emailDTO = new EmailDTO(searchVO.getPage(), emailService.countMyInbox(searchVO)); 
//		model.addAttribute("EmailDTO", emailDTO);
//        //model.addAttribute("searchVO", searchVO);
//		
//		return "email/inboxList :: #inboxTable";
//	}
	
	// 받은메일 상세조회
	@GetMapping("inboxInfo")
	public String inboxInfo(EmailVO emailVO, Model model) {
		// rfindVO 객체에 Service의 실행 결과를 담는다
		EmailVO rfindVO = emailService.inboxInfo(emailVO);
		// 데이터를 전달하는 model 객체에 rfindVO와 페이지에 제공될 이름 "emailInfo"를 담는다
		model.addAttribute("inboxInfo", rfindVO);
		// 담은 것들을 가지고 아래 페이지로 이동하게 한다.
		return "email/inboxInfo";
	}
	
	// 메일작성
	@GetMapping("emailWrite")
	public String empInsertForm(Model model, Authentication authentication) {
		//세션이 아니라 어센티케이션에서 로그인한 유저의 id를 얻어옴.
		Object principal = authentication.getPrincipal();
		if (principal instanceof LoginUserVO) {
			LoginUserVO loginUserVO = (LoginUserVO) principal;
			String empId = loginUserVO.getEmpId();
			String custNo = loginUserVO.getCustNo();
			
			EmpVO empVO = new EmpVO();
			empVO.setEmpId(empId);
			empVO.setCustNo(custNo);
			
			//내가 누군지 알아야 내가 속한 회사의 empList를 가져올 수 있다.
			List<EmpVO> list = emailService.getEmpList(empVO);
			model.addAttribute("getEmpList", list);
			return "email/emailWrite";
		} 
		return "/";
	}
	
	// 주소록 가져와서 모달창에 뿌려주기 위한.(페이지가 아닌 데이터를 리턴하기 위한.)
//	@ResponseBody
//	public List<EmpVO> getEmpList(@RequestBody EmpVO empVO, HttpServletRequest req) {
//		HttpSession session = req.getSession();
//		String myCustNo = (String) session.getAttribute("custNo");
//		EmpVO myEmpVO = new EmpVO();
//		//사용자 세션의 custNo 정보를 담은 EmpVO를 파라미터로 SQL(Mapper)로 전달한다, 페이지는 전달 X
//		myEmpVO.setCustNo(myCustNo);
//		
//		return emailService.getEmpList(myEmpVO);
//	}
	
	
	@PostMapping("emailSend")
	public String emailSend(EmailVO emailVO, MultipartFile[] filelist) {
		//리퀘스트 바디(교재 367쪽. 전달된 요청의 바디(ajax로 넘어옴)를 emailVO객체에 자동 매핑(필드명을 맞춰야 함)
		//수정. 그냥 폼데이터 받는걸로 변경	
		int SenEmailNo = emailService.emailInsert(emailVO);
		if(SenEmailNo != -1) {
			return"/"; //redirect:emailInfo?senEmailNo=" + SenEmailNo; //인서트 성공시, 보낸메일 상세조회 페이지 이동.
		}
		if(!filelist[0].isEmpty()) {
			emailFileUploadService.uploadFileInfo(filelist, emailVO.getSender(), emailVO.getSenEmailNo() );
		}
		return "email/emailWrite";
	}
	
	// 보낸메일 전체조회
	@GetMapping("emailList")
	public String emailList(SearchVO searchVO, Model model) {
		if(searchVO.getPage() == 0) {
			searchVO.setPage(1);
		}
		List<EmailVO> list = emailService.emailList();
		model.addAttribute("emailList", list);
		PageDTO pageDTO = new PageDTO(searchVO.getPage(), emailService.countSend());
		model.addAttribute("pageDTO", pageDTO);
		return "email/emailList";
	}
		
	// 보낸메일 상세조회
	@GetMapping("emailInfo")
	public String emailInfo(EmailVO emailVO, Model model) {
		EmailVO sfindVO = emailService.emailInfo(emailVO);
		model.addAttribute("emailInfo", sfindVO);
		return "email/emailInfo";
	}
	
	// 주고받은메일(ChainMailNo)로 엮인 메일들을 조회하는.
	@ResponseBody
	public List<EmailVO> ChainMailList(@RequestBody EmailVO emailVO, HttpServletRequest req) {
		//메일 생성 시 자동으로 chain_mail_no가 생성됨
		//아깝긴 한데 반드시 활용한다는 생각은 하지말자.
		EmailVO chainMailVO = new EmailVO();
		HttpSession session = req.getSession();
		return emailService.chainMailList();
	}
	
	
	// 휴지통 전체조회
	@GetMapping("wastedList")
	public String wastedmail(SearchVO searchVO, Model model) {
		if(searchVO.getPage() == 0) {
			searchVO.setPage(1);
		}
		List<EmailVO> list = emailService.wastedList();
		model.addAttribute("emailList", list);
		PageDTO pageDTO = new PageDTO(searchVO.getPage(), emailService.countWasted());
		model.addAttribute("pageDTO", pageDTO);
		return "email/wastedMail";
	}
	
	
	
}
