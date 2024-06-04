package com.samjo.app.email.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
	
	
	
	////////////////////////////////////받 은 메 일 ///////////////////////////////////////
	// 받은메일 전체조회
	@GetMapping("cust/inboxList")
	public String inboxList(SearchVO searchVO, Model model) {
		// 페이지 세팅
		if (searchVO.getPage() <= 0) {
			searchVO.setPage(1);
		}
		if (searchVO.getEmSort() == null || searchVO.getEmSort().trim().isEmpty()) {
			searchVO.setEmSort("s.sent_Dt DESC");
		}
		// 받은 메일의 수신자. 접속중인 계정 정보 받아오기.
		EmpVO empVO = SecuUtil.getLoginEmp();

		// 접속중인 계정의 id와, 고객사id 받아오기.
		String custNo = empVO.getCustNo();
		String recp = empVO.getEmpId();
		// searchVO에 담기.
		searchVO.setRecp(recp);
		searchVO.setCustNo(custNo);

		// 매퍼 쿼리에 searchVO를 실어 보내고, 결과를 받아 list에 담기.
		List<EmailVO> list = emailService.inboxList(searchVO);
		
		// 이 list는 타임리프로 보낼 것이므로, model에 담기.
		model.addAttribute("list", list);

		// PageDTO에 해당하는 EmailDTO에
		EmailDTO emailDTO = new EmailDTO(searchVO.getPage(), emailService.countMyInbox(searchVO));
		model.addAttribute("EmailDTO", emailDTO);
		model.addAttribute("searchVO", searchVO);
		model.addAttribute("empVO", empVO);
		//EmailVO nameVO = new EmailVO(emailService.getEmpName(nameVO));
		return "email/inboxList";
	}

	// 받은메일 검색/페이징 처리 AJAX 받는 걸로 바꿔보기
	@PostMapping("cust/viewInboxList")
	public String viewInboxPage(@RequestBody SearchVO searchVO, Model model) {
		if (searchVO.getPage() <= 0) {
			searchVO.setPage(1);
		}
		if (searchVO.getEmSort() == null || searchVO.getEmSort().trim().isEmpty()) {
			searchVO.setEmSort("s.sent_Dt DESC");
		}
		EmpVO empVO = SecuUtil.getLoginEmp();
		String custNo = empVO.getCustNo();
		String eid = empVO.getEmpId();
		searchVO.setRecp(eid);
		searchVO.setCustNo(custNo);
		List<EmailVO> list = emailService.inboxList(searchVO);
		model.addAttribute("list", list);
		EmailDTO emailDTO = new EmailDTO(searchVO.getPage(), emailService.countMyInbox(searchVO));
		model.addAttribute("EmailDTO", emailDTO);
		model.addAttribute("searchVO", searchVO);

		return "email/inboxList :: #inboxTable";
	}

	// 받은메일 상세조회 -> 전체조회에서 행을 클릭하고 넘어옴
	// 어소리티에서 계정id값, 고객번호값 받아와서, 그걸 매퍼까지 들고 간다
	// 쿼리에 영향을 끼치게 되므로, 로그인 한 사람은 자기와 관련된 정보만 받게 된다.
	@GetMapping("cust/inboxInfo/{senEmailNo}")
	public String inboxInfo(@PathVariable String senEmailNo, Model model) {
		EmpVO currentUser = SecuUtil.getLoginEmp();
		// VO 객체에 Service의 실행 결과를 담는다
		// 데이터를 전달하는 model 객체에 rfindVO와 페이지에 제공될 이름 "emailInfo"를 담는다
		EmailVO emailVO = emailService.inboxInfo(senEmailNo);
		//emailService.getEmpName(emailVO);
		String recp = emailVO.getRecp();
		String refer = emailVO.getRefer();
		String userId = currentUser.getEmpId();
		
		// 로그인 유저id가, 읽으려는 메일의 수신자, 참조자 어느것에도 해당하지 않는 경우 (하나라도 해당시 조회가능)
		// (즉 url에 메일 PK만 적어서 남의 메일을 보려 할 경우) => 홈으로 튕겨 보낸다.
		// 접속자가 수신자가 아니고(AND) 참조자가 NULL이면서 접속자가 참조자도 아닌 경우
		if (!recp.equals(userId)) {
		    if (refer == null || !refer.equals(userId)) {
		        return "redirect:/home";
		    }
		}
		System.out.println("지금 VO에 담긴 첨부파일 : " + emailVO.getFiles());
		model.addAttribute("emailVO", emailVO);
		// 담은 것들을 가지고 아래 페이지로 이동하게 한다.

		return "email/inboxInfo";
	}
/////////////////////////////////////////////// 보 낸 메 일 ///////////////////////////////////////////////
	// 보낸메일 전체조회
	@GetMapping("cust/emailList")
	public String emailList(SearchVO searchVO, Model model) {
		if (searchVO.getPage() <= 0) {
			searchVO.setPage(1);
		}
		if (searchVO.getEmSort() == null || searchVO.getEmSort().trim().isEmpty()) {
			searchVO.setEmSort("s.sent_Dt DESC");
		}
		EmpVO empVO = SecuUtil.getLoginEmp();

		String custNo = empVO.getCustNo();
		String sender = empVO.getEmpId();
		// searchVO에 담기.
		searchVO.setSender(sender);
		searchVO.setCustNo(custNo);

		// 매퍼 쿼리에 searchVO를 실어 보내고, 결과를 받아 list에 담기.
		List<EmailVO> list = emailService.emailList(searchVO);

		// 이 list는 타임리프로 보낼 것이므로, model에 담기.
		model.addAttribute("list", list);

		// PageDTO에 해당하는 EmailDTO에
		EmailDTO emailDTO = new EmailDTO(searchVO.getPage(), emailService.countMyEmail(searchVO));
		model.addAttribute("EmailDTO", emailDTO);
		model.addAttribute("searchVO", searchVO);

		return "email/emailList";
	}
	
	// 페이징 갈아끼우기 (보낸메일)
	@PostMapping("cust/viewEmailList")
	public String viewEmailPage(@RequestBody SearchVO searchVO, Model model) {
		if (searchVO.getPage() <= 0) {
			searchVO.setPage(1);
		}
		if (searchVO.getEmSort() == null || searchVO.getEmSort().trim().isEmpty()) {
			searchVO.setEmSort("s.sent_Dt DESC");
		}
		EmpVO empVO = SecuUtil.getLoginEmp();
		String custId = empVO.getCustNo();
		String eid = empVO.getEmpId();
		searchVO.setSender(eid);
		searchVO.setCustNo(custId);
		List<EmailVO> list = emailService.emailList(searchVO);
		model.addAttribute("list", list);
		EmailDTO emailDTO = new EmailDTO(searchVO.getPage(), emailService.countMyEmail(searchVO));
		model.addAttribute("EmailDTO", emailDTO);
		model.addAttribute("searchVO", searchVO);

		return "email/emailList :: #emailTable";
	}
	
	// 보낸메일 상세조회
	@GetMapping("cust/emailInfo/{senEmailNo}")
	public String emailInfo(@PathVariable String senEmailNo, Model model) {
		EmpVO currentUser = SecuUtil.getLoginEmp();
		// VO 객체에 Service의 실행 결과를 담는다
		EmailVO emailVO = emailService.emailInfo(senEmailNo);
		// 데이터를 전달하는 model 객체에 rfindVO와 페이지에 제공될 이름 "emailInfo"를 담는다
		String sender = emailVO.getSender();
		String userId = currentUser.getEmpId();
		
		// 발신자가 내가 아니면.
		// (즉 url에 메일 PK만 적어서 남의 메일을 보려 할 경우) => 홈으로 튕겨 보낸다.
		if (!sender.equals(userId)) {
			return "redirect:/home";
		}

		model.addAttribute("emailVO", emailVO);
		System.out.println(emailVO.getFiles());
		// 담은 것들을 가지고 아래 페이지로 이동하게 한다.

		return "email/emailInfo";
	}

	////////////////////////////////////////////메 일 쓰 기/////////////////////////////////////////
	// 메일작성
	@GetMapping("cust/emailWrite")
	public String empInsertForm(Model model, Authentication authentication) {
		// 세션이 아니라 어센티케이션에서 로그인한 유저의 id를 얻어옴.
		Object principal = authentication.getPrincipal();
		if (principal instanceof LoginUserVO) {
			LoginUserVO loginUserVO = (LoginUserVO) principal;
			String empId = loginUserVO.getEmpId();
			String custNo = loginUserVO.getCustNo();

			EmpVO empVO = new EmpVO();
			empVO.setEmpId(empId);
			empVO.setCustNo(custNo);

			// 내가 누군지 알아야 내가 속한 회사의 empList를 가져올 수 있다.
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

	@PostMapping("cust/emailSend")
	public String emailSend(EmailVO emailVO, MultipartFile[] filelist) {
		// 리퀘스트 바디(교재 367쪽. 전달된 요청의 바디(ajax로 넘어옴)를 emailVO객체에 자동 매핑(필드명을 맞춰야 함)
		// 수정. 그냥 폼데이터 받는걸로 변경
		
		List<Map<String, Object>> fileInfoList = new ArrayList<Map<String, Object>>();
		if (!filelist[0].isEmpty()) {
			fileInfoList = emailFileUploadService.uploadFileInfo(filelist);
		}
		
		//파일 저장과 인서트를 한 트랜잭션으로 묶는 작업.
		int result = emailService.emailInsert(emailVO ,fileInfoList);
		
		if (result != -1) {
			return "redirect:/cust/emailWrite";
		} else if (result == 0) {
			return "redirect:/cust/emailWrite";
		} else {
			return "redirect:/cust/emailList";
		}
	}
	



	// 주고받은메일(ChainMailNo)로 엮인 메일들을 조회하는.
	@ResponseBody
	public List<EmailVO> ChainMailList(@RequestBody EmailVO emailVO, HttpServletRequest req) {
		// 메일 생성 시 자동으로 chain_mail_no가 생성됨
		// 아깝긴 한데 반드시 활용한다는 생각은 하지말자.
		EmailVO chainMailVO = new EmailVO();
		HttpSession session = req.getSession();
		return emailService.chainMailList();
	}
////////////////////////////////////////////////휴 지 통///////////////////////////////////////////
	
	// 셀렉트박스 체크하고 휴지통 버튼 눌렀을 때 오는 ajax를 받아 처리
	@PostMapping("cust/emailList/goWaste")
	public String deleteEmail(@RequestBody List<String> senEmailNoList) {
	    try {
	        int deletedCount = emailService.deleteEmail(senEmailNoList);
	        return "redirect:/cust/emailList";
	    } catch (Exception e) {
	        e.printStackTrace();
	        return "redirect:/cust/emailList";
	    }
	}
	
	@PostMapping("cust/inboxList/goWaste")
	public String deleteInbox(@RequestBody List<String> recEmailNoList) {
	    try {
	        int deletedCount = emailService.deleteInbox(recEmailNoList);
	        return "redirect:/cust/inboxList";
	    } catch (Exception e) {
	        e.printStackTrace();
	        return "redirect:/cust/inboxList";
	    }
	}
	
	// 휴지통 전체조회
	@GetMapping("cust/wastedList")
	public String wastedList(SearchVO searchVO, Model model) {
		if (searchVO.getPage() <= 0) {
			searchVO.setPage(1);
		}
		if (searchVO.getEmSort() == null || searchVO.getEmSort().trim().isEmpty()) {
			searchVO.setEmSort("s.sent_Dt DESC");
		}
		EmpVO empVO = SecuUtil.getLoginEmp();
		String custNo = empVO.getCustNo();
		String eid = empVO.getEmpId();
		searchVO.setSender(eid);
		searchVO.setRecp(eid);
		searchVO.setRefer(eid);
		searchVO.setCustNo(custNo);
		
		List<EmailVO> list = emailService.wastedList(searchVO);
		model.addAttribute("list", list);
		EmailDTO emailDTO = new EmailDTO(searchVO.getPage(), emailService.countWasted(searchVO));
		model.addAttribute("EmailDTO", emailDTO);
		model.addAttribute("searchVO", searchVO);
		
		return "email/wastedList";
	}
	
	// 페이징 갈아끼우기 (휴지통)
	@PostMapping("cust/viewWastedList")
	public String viewWastedPage(@RequestBody SearchVO searchVO, Model model) {
		if (searchVO.getPage() <= 0) {
			searchVO.setPage(1);
		}
		if (searchVO.getEmSort() == null || searchVO.getEmSort().trim().isEmpty()) {
			searchVO.setEmSort("s.sent_Dt DESC");
		}
		EmpVO empVO = SecuUtil.getLoginEmp();
		String custNo = empVO.getCustNo();
		String eid = empVO.getEmpId();
		searchVO.setSender(eid);
		searchVO.setRecp(eid);
		searchVO.setRefer(eid);
		searchVO.setCustNo(custNo);
		List<EmailVO> list = emailService.wastedList(searchVO);
		model.addAttribute("list", list);
		EmailDTO emailDTO = new EmailDTO(searchVO.getPage(), emailService.countWasted(searchVO));
		model.addAttribute("EmailDTO", emailDTO);
		model.addAttribute("searchVO", searchVO);

		return "email/wastedList :: #wastedTable";
	}
	
	// 휴지통 복원하기
	@PostMapping("cust/wastedList/goRestore")
	public String restoreMail(@RequestBody List<String> senEmailNoList) {
	    try {
	        emailService.restoreMail(senEmailNoList);
	        return "redirect:/cust/wastedList";
	    } catch (Exception e) {
	        e.printStackTrace();
	        return "redirect:/cust/wastedList";
	    }
	}
	
	// 휴지통 완전삭제
	@PostMapping("cust/wastedList/goRemove")
	public String removeMail(@RequestBody List<String> senEmailNoList) {
	    try {
	        emailService.removeMail(senEmailNoList);
	        return "redirect:/cust/wastedList";
	    } catch (Exception e) {
	        e.printStackTrace();
	        return "redirect:/cust/wastedList";
	    }
	}
	
}
