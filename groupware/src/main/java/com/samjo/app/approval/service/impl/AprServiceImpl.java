package com.samjo.app.approval.service.impl;

import java.util.HashMap;
import java.util.Map;

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
	public Map<String, Object> goToApr(AprVO aprVO) {
		Map<String, Object> map = new HashMap<>();
		aprMapper.updateAprGo(aprVO);
		map.put("OUT", aprVO.getResult());
		return map;
	}

	@Override
	public Map<String, Object> aprOk(AprVO aprVO) {
		Map<String, Object> map = new HashMap<>();
		aprMapper.updateAprOk(aprVO);
		map.put("OUT", aprVO.getResult());
		return map;
	}

	@Override
	public Map<String, Object> aprNg(AprVO aprVO) {
		if(aprVO.getReCmt() == null) {
			aprVO.setReCmt("no comment");
		}
		Map<String, Object> map = new HashMap<>();
		aprMapper.updateAprNg(aprVO);
		map.put("OUT", aprVO.getResult());
		return map;
	}


}
