package com.samjo.app.email.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.samjo.app.common.service.SearchVO;
import com.samjo.app.email.service.EmailFileVO;
import com.samjo.app.email.service.EmailVO;
import com.samjo.app.emp.service.EmpVO;

// Service 인터페이스와 1:1 매치가 되는 구조가 되어버렸는데, 다른 조원은 어떤지 확인해보기
// 네이밍도 어떻게 했는지 살펴보기
public interface EmailMapper {
	// 받은메일 전체조회
	public List<EmailVO> selectInboxAll(SearchVO searchVO);
	
	// 받은메일 상세조회
	public EmailVO selectInbox(String senEmailNo);
	
	// 메일 발송(emailSend)
	public int insertEmail(EmailVO emailVO);
	
	// 메일 첨부파일 등록
	public int insertEmailFile(EmailFileVO emailFileVO);
	
	// 보낸메일 전체조회
	public List<EmailVO> selectEmailAll(SearchVO searchVO);
	
	// 보낸메일 상세조회
	public EmailVO selectEmail(String senEmailNo);
	
	// 휴지통 전체조회
	public List<EmailVO> wastedList(SearchVO searchVO);
	
	// 메일삭제/휴지통 (단, DB에 데이터를 없애는 것이 아님. 메일 상태 칼럼값을 변경한다.)
	public EmailVO deleteEmail(EmailVO emailVO);
	
	// 답신할 경우, 해당하는 수신메일의 정보를 가져오기 => 체인메일넘버 유념
	public EmailVO getInboxNo(EmailVO emailVO);
	
	// 주고받은 메일 조회
	public List<EmailVO> chainMailList();
	
	// 발신하는 이메일에 파일 첨부
	public EmailVO setFile(EmailVO emailVO);
		
	// 수신한 이메일의 파일 내려받기
	public EmailVO getFile(EmailVO emailVO);
	
	//이메일 전체 페이징
	public int count(@Param("eid")String empId);
	
	// 한 emp가 recp이거나, refer인 메일
	public int countMyInbox(SearchVO searchVO);
	
	public int countMyEmail(SearchVO searchVO);
	
	//전체 페이지(보낸메일)
	public int countSend();
	
	//전체 페이지(휴지통)
	public int countWasted(SearchVO searchVO);
	
	//휴지통 복원
	public List<EmailVO> restoreMail();
	
	// 주소록 조회
	public List<EmpVO> getEmpList(EmpVO empVO);
}
