package com.samjo.app.cust.service;

public interface CustService {
	
	// 고객사 등록
	public String insertCust(CustVO custVO);
	// 고객사 단건조회
	public CustVO selectCustInfo(String custNo);

}
