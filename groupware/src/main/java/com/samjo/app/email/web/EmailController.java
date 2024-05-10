package com.samjo.app.email.web;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.samjo.app.email.service.EmailService;
import com.samjo.app.email.service.EmailVO;


@Controller
public class EmailController {
	
	@Autowired
	EmailService emailService;
	
	// 받은메일 전체조회
	@GetMapping("/email/inboxList")
	public String inboxList(Model model) {
		List<EmailVO> list = emailService.inboxList();
		model.addAttribute("inboxList", list);
		return "email/inboxList";
	}
	
	// 받은메일 상세조회
	@GetMapping("/email/inboxInfo")
	public String inboxInfo(EmailVO emailVO, Model model) {
		// rfindVO 객체에 Service의 실행 결과를 담는다
		EmailVO rfindVO = emailService.inboxInfo(emailVO);
		// 데이터를 전달하는 model 객체에 rfindVO와 페이지에 제공될 이름 "emailInfo"를 담는다
		model.addAttribute("inboxInfo", rfindVO);
		// 담은 것들을 가지고 아래 페이지로 이동하게 한다.
		return "email/inboxInfo";
	}
	
	// 메일 작성 2가지. 입력 전/입력 후
	
//	@GetMapping("empInsert")
//	public String empInsertForm(Model model) {
//		EmailVO emailVO =
//		model.addAttribute("emailVO", emailVO);
//		return "emp/insert";
//	}
	
	//등록 - 처리 => Post (데이터를 리퀘스트 바디에 실어 넘겨야 하므로)
//	@PostMapping("empInsert")
//	public String empInsertProcess(EmailVO emailVO) {
//		int eId = emailService.emailInsert(emailVO);
//		String uri = null;
//		if(eId > -1) {
//			uri = "redirect:empInfo?employeeId=" + eId;
//		} else {
//			uri = "empList";
//		}
//		return uri;
//	}
	
	// 보낸메일 전체조회
	@GetMapping("/email/emailList")
	public String emailList(Model model) {
		List<EmailVO> list = emailService.emailList();
		model.addAttribute("emailList", list);
		return "email/emailList";
	}
		
	// 보낸메일 상세조회
	@GetMapping("/email/emailInfo")
	public String emailInfo(EmailVO emailVO, Model model) {
		EmailVO sfindVO = emailService.emailInfo(emailVO);
		model.addAttribute("emailInfo", sfindVO);
		return "email/emailInfo";
	}

}
