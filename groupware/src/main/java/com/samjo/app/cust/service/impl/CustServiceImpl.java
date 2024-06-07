package com.samjo.app.cust.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samjo.app.common.service.SearchVO;
import com.samjo.app.cust.mapper.CustMapper;
import com.samjo.app.cust.service.CustService;
import com.samjo.app.cust.service.CustVO;

@Service
public class CustServiceImpl implements CustService {

	@Autowired
	CustMapper custMapper;
	
	// 고객사 등록.
	@Override
	public String insertCust(CustVO custVO) {
		int result = 0;
		result = custMapper.insertCust(custVO);
		
		if(result == 0) {
			return null;
		}
		
		return custVO.getCustNo();
	}
	
	// 고객사 단건조회.
	@Override
	public CustVO selectCustInfo(String custNo) {
		return custMapper.selectCustInfo(custNo);
	}
	
	// 고객사 전체조회
	@Override
	public List<CustVO> selectCusts(SearchVO search) {
		return custMapper.selectCusts(search);
	}
	// 고객사 전체조회 카운트.
	@Override
	public int countCusts(SearchVO search) {
		return custMapper.countCusts(search);
	}

}
