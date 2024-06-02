package com.samjo.app.pay.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.samjo.app.common.service.SearchVO;

public interface PayService {

	//결제전체조회
	public List<PayVO> payList(SearchVO searchVO);
	//계약 페이징
	public int count(SearchVO searchVO);
	
	/*결제처리 테스트*/
	public String getToken();
	public String requestSubPay();
	public String schedulePay(String customer_uid, int price);
	
	//정기결제 내역조회
	public String payLList() throws ParseException;
	public long dateStamp(String date) throws ParseException;
}
