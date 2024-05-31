package com.samjo.app.email.service;

import java.util.List;

import com.samjo.app.common.service.SearchVO;
import com.samjo.app.emp.service.EmpVO;

public interface EmailService {
	// 받은메일 전체조회
	public List<EmailVO> inboxList(SearchVO searchVO);
	
	// 받은메일 상세조회
	public EmailVO inboxInfo(String senEmailNo);
	
	// 메일 작성
	public int emailInsert(EmailVO emailVO);
	
	// 모달에서 사용할 같은 고객사 사원 리스트(일부 정보만 보여주기)
	// 주소록 조회(해당 고객사의 사원리스트를 일부 정보만 표시한 채로 전부 출력, 계정쪽 기능 완성 후에 작성한다. 
	public List<EmpVO> getEmpList(EmpVO empVO);
	
	// 메일 파일 등록
	public int EmailFileInsert(EmailFileVO emailFileVO); 
		
	// 보낸메일 전체조회
	public List<EmailVO> emailList(SearchVO searchVO);
	
	// 보낸메일 상세조회
	public EmailVO emailInfo(String senEmailNo);
	
	// 메일삭제/휴지통 (단, DB에 데이터를 없애는 것이 아님. 메일 상태 칼럼값을 변경한다.)
	public EmailVO deleteEmail(EmailVO emailVO);
	
	// 휴지통 전체조회
	//휴지통의 메일종류(발신,수신) 구분하기.
	public List<EmailVO> wastedList(SearchVO searchVO);
	
	// 답신할 경우, 해당하는 수신메일의 정보를 가져오기 => 체인메일넘버 유념
	public EmailVO getInboxNo(EmailVO emailVO);
	
	// 주고받은 메일 조회
	public List<EmailVO> chainMailList();
	
	// 발신하는 이메일에 파일 첨부
	public EmailVO setFile(EmailVO emailVO);
	
	// 수신한 이메일의 파일 내려받기
	public EmailVO getFile(EmailVO emailVO);
	
	//뭔가 잘못됨 이건 나중에 지우자
	public int count(String empId);
	
	//받은메일 페이징
	public int countMyInbox(SearchVO searchVO);
	
	//받은메일 페이징
	public int countMyEmail(SearchVO searchVO);
	
	//전체 페이징(보낸메일)
	public int countSend();
	
	//전체 페이징(휴지통)
	public int countWasted(SearchVO searchVO);
	
	//휴지통 복원
	public List<EmailVO> restoreMail();
	
}
