package com.samjo.app.email.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.samjo.app.common.service.PageDTO;
import com.samjo.app.common.service.SearchVO;
import com.samjo.app.email.service.EmailService;
import com.samjo.app.email.service.EmailVO;
import com.samjo.app.emp.service.EmpVO;

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
		//model.addAttribute("empId", "test01");
	return "email/emailWrite";
	}
	
	// 주소록 가져와서 모달창에 뿌려주기 위한.(페이지가 아닌 데이터를 리턴하기 위한.)
	@ResponseBody
	public List<EmpVO> getEmpList(@RequestBody EmpVO empVO, HttpServletRequest req) {
		HttpSession session = req.getSession();
		String myCustNo = (String) session.getAttribute("custNo");
		EmpVO myEmpVO = new EmpVO();
		//사용자 세션의 custNo 정보를 담은 EmpVO를 파라미터로 SQL(Mapper)로 전달한다, 페이지는 전달 X
		myEmpVO.setCustNo(myCustNo);
		
		return emailService.getEmpList(myEmpVO);
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
	
	// 주고받은메일(ChainMailNo)로 엮인 메일들을 조회하는.
	@ResponseBody
	public List<EmailVO> ChainMailList(@RequestBody EmailVO emailVO, HttpServletRequest req) {
		//수신 상세화면에서, 답신을 눌렀을 경우 chain_mail_no를 부여?
		//아니면, 메일 생성시 자동(selectKey)로 생성?
		EmailVO chainMailVO = new EmailVO();
		HttpSession session = req.getSession();
		// 예상대로 로직 구상부터 만만찮다. 일단 여기까지 ㅠ(0517)
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
