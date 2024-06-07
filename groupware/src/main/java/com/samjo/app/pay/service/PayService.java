package com.samjo.app.pay.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.samjo.app.common.service.SearchVO;

public interface PayService {

	//결제전체조회
	public List<PayVO> payList(SearchVO searchVO);
	
	//결제 페이징
	public int count(SearchVO searchVO);

	//결제처리용 토큰발급
	public String getToken();
	
	//최초 결제처리
	public String firstPay(String customer_uid, int price, int ctNo);
	
	//결제 등록 및 DB반영
	public String schedulePay(String customer_uid, int price, int ctNo);

	//예약된 결제건 입금여부 확인
	public String payResultCheck(long merchantUid, int ctNo);
	
	//정기결제 내역조회
	public String payLList() throws ParseException;
	public long dateStamp(String date) throws ParseException;
	
	//고객사 관리자 결제조회
	public List<PayVO> custPayList(SearchVO searchVO);
	
	//고객사 관리자 결제 페이징
	public int custCount(String custNo);
	
}
