package com.samjo.app.email.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samjo.app.email.mapper.EmailMapper;
import com.samjo.app.email.service.EmailService;
import com.samjo.app.email.service.EmailVO;

@Service
public class EmailServiceImpl implements EmailService {
	
	EmailMapper emailMapper;
	
	@Autowired
	public EmailServiceImpl(EmailMapper emailMapper) {
		this.emailMapper = emailMapper;
	}
	
	@Override
	public List<EmailVO> inboxList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EmailVO inboxInfo(EmailVO emailVo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int emailInsert(EmailVO emailVo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<EmailVO> emailList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EmailVO emailInfo(EmailVO emailVo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EmailVO deleteEmail() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EmailVO getInboxNo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmailVO> chainMailList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EmailVO setFile(EmailVO emailVo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EmailVO getFile(EmailVO emailVo) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
