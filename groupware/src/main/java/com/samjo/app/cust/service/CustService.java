package com.samjo.app.cust.service;

import java.util.List;

import com.samjo.app.common.service.SearchVO;

public interface CustService {
	
	// 고객사 등록
	public String insertCust(CustVO custVO);
	// 고객사 단건조회
	public CustVO selectCustInfo(String custNo);
	// 고객사 전체조회
	public List<CustVO> selectCusts(SearchVO search);
	public int countCusts(SearchVO search);

}
