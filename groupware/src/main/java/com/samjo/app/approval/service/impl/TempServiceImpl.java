package com.samjo.app.approval.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samjo.app.approval.mapper.TempMapper;
import com.samjo.app.approval.service.TempService;
import com.samjo.app.approval.service.TempVO;

@Service
public class TempServiceImpl implements TempService {

	@Autowired
	TempMapper tempMapper;
	
	@Override
	public Map<String, Object> insertPto(TempVO temp) {
		Map<String, Object> map = new HashMap<>();
		int result = tempMapper.insertPto(temp);
		if(result == 1) {
			map.put("result", "OK");
		} else {
			map.put("result", "NG");
		}
		return map;
	}

	@Override
	public TempVO selectPto(Integer docNo) {
		return tempMapper.selectPto(docNo);
	}
}
