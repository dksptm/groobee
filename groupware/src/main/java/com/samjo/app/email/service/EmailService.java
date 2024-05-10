package com.samjo.app.email.service;

import java.util.List;

public interface EmailService {
	// 받은메일 전체조회
	public List<EmailVO> inboxList();
	
	// 받은메일 상세조회
	public EmailVO inboxInfo(EmailVO emailVo);
	
	// 메일 작성
	public int emailInsert(EmailVO emailVo);
	
	// 메일 작성시, 발신자의 계정을 받아오는 -> 서비스메소드 말고 세션에서 받아오는것으로 처리하자.
	
	// 보낸메일 전체조회
	public List<EmailVO> emailList();
	
	// 보낸메일 상세조회
	public EmailVO emailInfo(EmailVO emailVo);
	
	// 메일삭제/휴지통 (단, DB에 데이터를 없애는 것이 아님. 메일 상태 칼럼값을 변경한다.)
	public EmailVO deleteEmail();
	
	// 답신할 경우, 해당하는 수신메일의 정보를 가져오기 => 체인메일넘버 유념
	public EmailVO getInboxNo();
	
	// 주고받은 메일 조회
	public List<EmailVO> chainMailList();
	
	// 발신하는 이메일에 파일 첨부
	public EmailVO setFile(EmailVO emailVo);
	
	// 수신한 이메일의 파일 내려받기
	public EmailVO getFile(EmailVO emailVo);
	
	// 주소록 조회(해당 고객사의 사원리스트를 일부 정보만 표시한 채로 전부 출력, 계정쪽 기능 완성 후에 작성한다. 
	// public List<EmpVO> getEmpList();
}
