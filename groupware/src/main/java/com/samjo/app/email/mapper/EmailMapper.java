package com.samjo.app.email.mapper;

import java.util.List;

import com.samjo.app.email.service.EmailVO;

// Service 인터페이스와 1:1 매치가 되는 구조가 되어버렸는데, 다른 조원은 어떤지 확인해보기
// 네이밍도 어떻게 했는지 살펴보기
public interface EmailMapper {
	// 받은메일 전체조회
	public List<EmailVO> selectInboxAll();
	
	// 받은메일 상세조회
	public EmailVO selectInbox(EmailVO emailVo);
	
	// 메일 작성
	public int insertEmail(EmailVO emailVo);
	
	// 보낸메일 전체조회
	public List<EmailVO> selectEmailAll();
	
	// 보낸메일 상세조회
	public EmailVO selectEmail(EmailVO emailVo);
	
	// 휴지통 전체조회
	public List<EmailVO> wastedList();
	
	// 메일삭제/휴지통 (단, DB에 데이터를 없애는 것이 아님. 메일 상태 칼럼값을 변경한다.)
	public EmailVO deleteEmail(EmailVO emailVo);
	
	// 답신할 경우, 해당하는 수신메일의 정보를 가져오기 => 체인메일넘버 유념
	public EmailVO getInboxNo(EmailVO emailVo);
	
	// 주고받은 메일 조회
	public List<EmailVO> chainMailList();
	
	// 발신하는 이메일에 파일 첨부
	public EmailVO setFile(EmailVO emailVo);
		
	// 수신한 이메일의 파일 내려받기
	public EmailVO getFile(EmailVO emailVo);
	
	//전체 페이지(받은메일)
	public int count();
	
	//전체 페이지(보낸메일)
	public int countSend();
	
	//전체 페이지(휴지통)
	public int countWasted();
	
	//휴지통 복원
	public List<EmailVO> restoreMail();
	
	// 주소록 조회(해당 고객사의 사원리스트를 일부 정보만 표시한 채로 전부 출력, 계정쪽 기능 완성 후에 작성한다. 
	// public List<EmpVO> getEmpList();
}
