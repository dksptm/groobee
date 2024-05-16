package com.samjo.app.email.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.samjo.app.common.service.PageDTO;
import com.samjo.app.common.service.SearchVO;
import com.samjo.app.email.service.EmailService;
import com.samjo.app.email.service.EmailVO;

@Controller
public class EmailController {
	
	@Autowired
	EmailService emailService;
	
	// 받은메일 전체조회
	@GetMapping("inboxList")
	public String inboxList(SearchVO searchVO, Model model) {
		if(searchVO.getPage() == 0) {
			searchVO.setPage(1);
		}
		List<EmailVO> list = emailService.inboxList();
		model.addAttribute("inboxList", list);
		PageDTO pageDTO = new PageDTO(searchVO.getPage(), emailService.count());
		model.addAttribute("pageDTO", pageDTO);
		return "email/inboxList";
	}
	
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
	// Insert에 Get / Post 둘다 매핑하는 이유 
	// => 처음 작성페이지에서는 빈값이 넘어옴 Get
	// => 폼 작성후 보낼때는 데이터를 리퀘스트바디에 싣고 넘어가야 함 Post
	@GetMapping("emailWrite")
	public String empInsertForm(Model model, HttpServletRequest req) {
		//내 경우 emp레퍼런스랑 다르게, 세션에서 계정 id를 받아와서 보내는사람 칸에 자동입력 시켜야함
		HttpSession session = req.getSession();
		//세션에서 로그인한 계정의 id를 받아온다.
		String empId = (String)session.getAttribute("empId");
		EmailVO emailVO = new EmailVO();
		emailVO.setSender(empId);
		model.addAttribute("email", emailVO); //이 부분은 로그인 기능 활성화되면 하자.
//		model.addAttribute("empId", "test01");
	return "email/emailWrite";
	}
	
	// 내 경우, 여기에서 작성한 데이터를 1:1로 받는 형식이다.
	@PostMapping("emailSend")
	public String emailSend(EmailVO emailVO) {
		int eId = emailService.emailInsert(emailVO);
		String uri = null;
		if(eId > -1) {
			uri = "redirect:empInfo?employeeId=" + eId;
		} else {
			uri = "email/emailSend";
		}
	return uri;
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
	
	// 주소록(그냥 해당고객사 전체사원 일부정보 가리고 출력하기로 해서.. 좀만 더 생각해보기)
	// 많이 어려울것 같지는 않다
	
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
