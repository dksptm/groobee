package com.samjo.app.approval.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samjo.app.approval.mapper.AprMapper;
import com.samjo.app.approval.service.AprService;
import com.samjo.app.approval.service.AprVO;

@Service
public class AprServiceImpl implements AprService {

	AprMapper aprMapper;
	
	@Autowired
	public AprServiceImpl(AprMapper aprMapper) {
		this.aprMapper = aprMapper;
	}

	@Override
	public int goToApr(AprVO aprVO) {
		aprVO = aprMapper.updateMyApr();
		return aprVO.getResult();
	}

}
